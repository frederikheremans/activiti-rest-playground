/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package be.stacktrace.activiti.rest.variable;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.rest.application.ActivitiRestServicesApplication;
import org.restlet.Component;
import org.restlet.data.Protocol;


/**
 * Simple main-class that starts a {@link ActivitiRestServicesApplication} subclass that contains
 * a custom variable-type. Runs a standalone Restlet-container.
 * 
 * @author Frederik Heremans
 */
public class CustomVariableRepresentationMain {

  public static void main(String[] args) throws Exception {
    
    // Create the Activiti engine in-memory on H2 database
    ProcessEngineConfiguration engineConfig = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
    ProcessEngine processEngine = engineConfig.buildProcessEngine();
    
    // Add a user to allow logging in through REST
    User newUser = processEngine.getIdentityService().newUser("kermit");
    newUser.setPassword("kermit");
    processEngine.getIdentityService().saveUser(newUser);
    
    // Deploy the famous "oneTaskProcess"
    processEngine.getRepositoryService().createDeployment()
      .addClasspathResource("oneTaskProcess.bpmn20.xml")
      .deploy();
    
    ProcessInstance processInstance = processEngine.getRuntimeService()
            .startProcessInstanceByKey("oneTaskProcess");
    
    // Set an instance of MyPojo as process-variable
    processEngine.getRuntimeService().setVariable(processInstance.getId(),
            "testVariable", new MyPojo("fred", "fred@fr.ed"));
    
    // Create Restlet-component and configure it to use HTTP on port 8182
    Component component = new Component();
    component.getServers().add(Protocol.HTTP, 8182);

    ActivitiRestServicesApplication application = new ActivitiRestServicesApplication();
    application.setRestResponseFactory(new CustomRestResponseFactory());
    
    // Attach the Activiti REST-application and start
    component.getDefaultHost().attach("/activiti-rest",
            application);
    component.start();
  }
}
