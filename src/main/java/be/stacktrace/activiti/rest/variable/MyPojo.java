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

import java.io.Serializable;


/**
 * @author Frederik Heremans
 */
public class MyPojo implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private String name;
  private String email;
  
  
  public MyPojo() {}
  
  
  public MyPojo(String name, String email) {
    this.name = name;
    this.email = email;
  }
  
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getEmail() {
    return email;
  }
}
