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

import java.lang.reflect.Field;
import java.util.List;

import org.activiti.rest.api.RestResponseFactory;
import org.activiti.rest.api.engine.variable.RestVariableConverter;


/**
 * @author Frederik Heremans
 */
public class CustomRestResponseFactory extends RestResponseFactory {

  @Override
  protected void initializeVariableConverters() {
    super.initializeVariableConverters();
    
    // Add custom converter for MyPojo variables
    getVariableConverters().add(new MyPojoVariableConverter());
  }
  
  @SuppressWarnings("unchecked")
  private List<RestVariableConverter> getVariableConverters() {
    try {
      // Workaround until the "variableConverters" field is set protected on RestResponseFactory or
      // the converters are exposed.
      Field f = RestResponseFactory.class.getDeclaredField("variableConverters");
      f.setAccessible(true);
      List<RestVariableConverter> variableConverters = ( List<RestVariableConverter> ) f.get(this);
      return variableConverters;
      
    } catch (SecurityException e) {
      throw new RuntimeException(e);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
