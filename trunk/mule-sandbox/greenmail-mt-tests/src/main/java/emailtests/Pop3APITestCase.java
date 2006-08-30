/**
 * 
 */
package emailtests;

import org.mule.extras.client.MuleClient;
import org.mule.umo.UMOMessage;


/**
 * @author <a href="mailto:stephen.fenech@symphonysoft.com">Stephen Fenech</a>
 * 
 * retrieve messages via the client directly
 *
 */
public class Pop3APITestCase extends MailFunctionalTestCase{

	protected void doPreFunctionalSetUp() throws Exception {
		super.doPreFunctionalSetUp();
		servers.setUser("mule@symphonysoft.com", "login", "password");
		
    }
	
	public void testPlainMessageAPI() throws Exception
	{
		MuleClient client=new MuleClient();
		UMOMessage msg=null;
		String result;
		final String subject = servers.util().random(); 
	    final String body = servers.util().random();
	    
		for(int i=0;i<messageCount;i++)
		{
			//Send Messages to Inbox
			servers.util().sendTextEmailTest("mule@symphonysoft.com", "from@symphonysoft.com", subject, body+" "+i);		    
			assertEquals(i+1, servers.getReceivedMessages().length);
		}
		
	    //Retrieve using Mule Client		
	    for(int i=0;i<messageCount;i++)
	    {
	    	msg=client.receive("pop3://login:password@localhost:3110", 100);
			assertNotNull(msg);
			assertTrue(msg.getPayload() instanceof String);
			result=(String)msg.getPayload();
			assertTrue(result.trim().compareTo(body+" "+i)==0);
	    }	
	}
	
	@Override
	protected String getConfigResources() {
		return "mule-empty-config.xml";
	}

}
