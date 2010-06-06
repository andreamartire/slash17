package com.netedit.core.nodes;

import java.io.Serializable;
import java.util.ArrayList;

import com.netedit.common.IpAddress;
import com.netedit.core.nodes.components.AbstractInterface;

/** 
 * Class representing a collision domain
 * 
 * @author sal
 */
public class CollisionDomain implements AbstractCollisionDomain, Serializable {
	private static final long serialVersionUID = -3392516789233618944L;

	protected String name;
	
	/** network area of this domain */
	protected String area;
	
	/** minimum number of ip address needed */
	protected int minIpAddr;
	
	/** subnet */
	protected String subnet;
	
	/** subnet mask */
	protected String subnetMask;
	
	/** range of usable ip addresses */
	protected String ipRange;
	
	/** hosts interfaces on this collision domain */
	protected ArrayList<AbstractInterface> hostsInterfaces;
	
	/** a boolean indicating if this collision domain is the tap domain */
	protected boolean isTap;
	
	/** 
	 * Create a collision domain with a minimum ip addresses required
	 * 
	 * @param name - (String) collision domain's name
	 * @param minIpAddr - (int) minimum ip addresses required
	 */
	CollisionDomain( String name, int minIpAddr ) {
		this.minIpAddr = minIpAddr;
		this.name = name;
		this.hostsInterfaces = new ArrayList<AbstractInterface>();
		this.isTap = false;
	}

	/** 
	 * Create a collision domain
	 * 
	 * @param name - (String) collision domain's name
	 */
	public CollisionDomain( String name ) {
		this.name = name;
		this.hostsInterfaces = new ArrayList<AbstractInterface>();
		this.isTap = false;
	}
	
	/** connect a host's interface to this domain */
	public void addConnection( AbstractInterface hostInterface ) {
		hostsInterfaces.add(hostInterface);
	}
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
		this.name = name + " (" + area + ")";
	}

	public int getMinIpAddr() {
		return minIpAddr;
	}

	public void setMinIpAddr(int minIpAddr) {
		this.minIpAddr = minIpAddr;
	}

	public String getSubnet() {
		return subnet;
	}

	public boolean setSubnet(String subnet) {
		if( subnet.matches( IpAddress.ipRx )) {
			this.subnet = subnet;
			return true;
		} else {
			return false;
		}
	}

	public String getSubnetMask() {
		return subnetMask;
	}

	public boolean setSubnetMask(String subnetMask) {
		if( subnetMask.matches( IpAddress.maskRx )) {
			this.subnetMask = subnetMask;
			return true;
		} else {
			return false;
		}
	}

	public String getIpRange() {
		return ipRange;
	}

	public boolean setIpRange(String ipRange) {
		if( ipRange.matches( IpAddress.ipRx + "-" + IpAddress.ipRx )) {
			this.ipRange = ipRange;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delete() {
		while( !hostsInterfaces.isEmpty() ) {
			hostsInterfaces.get(0).delete();
		}
		return true;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public void removeConnection(AbstractInterface hostInterface) {
		this.hostsInterfaces.remove(hostInterface);
	}
	
	public ArrayList<AbstractInterface> getConnectedInterfaces() {
		return hostsInterfaces;
	}

	@Override
	public void setIsTap( boolean isTap ) {
		this.isTap = isTap;
	}
	
	@Override
	public boolean isTap() {
		return isTap;
	}
	
	@Override
	public void setMinimumIp( int min ) {
		this.minIpAddr = min;
	}

	@Override
	public int getMinimumIp() {
		return minIpAddr;
	}
}