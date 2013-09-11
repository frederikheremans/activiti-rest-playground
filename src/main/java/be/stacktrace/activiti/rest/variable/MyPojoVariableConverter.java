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

import java.util.Map;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.rest.api.engine.variable.RestVariable;
import org.activiti.rest.api.engine.variable.RestVariableConverter;


/**
 * @author Frederik Heremans
 */
public class MyPojoVariableConverter implements RestVariableConverter {

  public String getRestTypeName() {
    return "mypojo";
  }

  public Class< ? > getVariableType() {
    return MyPojo.class;
  }

  @SuppressWarnings("unchecked")
  public Object getVariableValue(RestVariable result) {
    // Jackson deserializes all unknown JSON-structures as a map, containing the properties, 
    // which are potentially also Maps of properties
    Map<String, Object> jsonProperties = (Map<String, Object>) result.getValue();
    
    // Some validation on the given input, in real use-cases additional checks are needed (eg. correct types)
    if(!jsonProperties.containsKey("name") || !jsonProperties.containsKey("email")) {
      // An ActivitiIllegalArgumentException will result in a 400 - BAD REQUEST
      throw new ActivitiIllegalArgumentException("The request body should contain both name and email.");
    }
    
    return new MyPojo((String) jsonProperties.get("name"), (String)jsonProperties.get("email"));
  }

  public void convertVariableValue(Object variableValue, RestVariable result) {
    // Let Jackson handle the serialisation of the POJO to JSON
    result.setValue(variableValue);
  }

}
