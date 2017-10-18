/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes04;

import java.util.HashMap;
import java.util.Map;

import jadex.base.Starter;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IExternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.search.SServiceProvider;
import jadex.bridge.service.types.cms.CreationInfo;
import jadex.bridge.service.types.cms.IComponentManagementService;
import jadex.commons.future.IFuture;
import jadex.commons.future.ThreadSuspendable;

public class Main {
	public static void main(String[] args) {

		// Merge arguments and default arguments.
		String[] defargs = new String[] { "-gui", "true", "-welcome", "true", "-cli", "true", "-printpass", "true" };
		String[] newargs = new String[defargs.length + args.length];
		System.arraycopy(defargs, 0, newargs, 0, defargs.length);
		System.arraycopy(args, 0, newargs, defargs.length, args.length);

		// Start the platform with the arguments.
		IFuture<IExternalAccess> platfut = Starter.createPlatform(newargs);

		// Wait until the platform has started and retrieve the platform access.
		ThreadSuspendable sus = new ThreadSuspendable();
		IExternalAccess platform = platfut.get(sus);
		System.out.println("Started platform: " + platform.getComponentIdentifier());

		// Get the CMS service from the platform
		IComponentManagementService cms = SServiceProvider.getService(platform.getServiceProvider(),
				IComponentManagementService.class, RequiredServiceInfo.SCOPE_PLATFORM).get(sus);

		// createComponent(AID, ClassPath, Arguments -> CreationInfo)
		
		IComponentIdentifier agentCID = cms.createComponent("PongAgent", "agentes04.PongAgent.class", null)
				.getFirstResult(sus);
		

		Map<String, Object> agentArgs = new HashMap<String, Object>();
		agentArgs.put("receiver", agentCID);
		CreationInfo cInfo = new CreationInfo(agentArgs);

		cms.createComponent("PingAgent", "agentes04.PingAgent.class", cInfo);

	}
}
