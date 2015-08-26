/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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

package in.antaragni.ant.datamodels;
import java.util.Random;

public class Contact
{
  private int serialNumber;
  private String name;
  private String post;
  private String number;
  private int drawable;

  public Contact(String name,String post, String number, int drawable)
  {
    this.name=name;
    this.post=post;
    this.number=number;
    this.drawable=drawable;
  }

  public String getName()
  {
    return name;
  }

  public String getPost()
  {
    return post;
  }

  public String getNumber()
  {
    return number;
  }

  public int getDrawable()
  {
    return drawable;
  }
}
