package project.core;

import java.util.ArrayList;

public interface AbstractChain {

	public String getName();
	
	public void setName( String name );
	
	public String getPolicy();
	
	public void setPolicy( String policy );
	
	public AbstractHost getHost();
	
	public ArrayList<AbstractRule> getRules();
	
	public AbstractRule getRule( String rule );
	
	public AbstractRule addRule();
	
	public void deleteRule( AbstractRule rule );

	public void delete();

	public String getConfCommand();

	public void ruleUp(AbstractRule rule);

	public void ruleDown(AbstractRule rule);
}
