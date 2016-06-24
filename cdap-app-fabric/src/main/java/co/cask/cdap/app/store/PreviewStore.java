/*
 * Copyright © 2016 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package co.cask.cdap.app.store;

import co.cask.cdap.api.preview.PreviewLogger;
import co.cask.cdap.proto.id.ProgramId;

import java.util.List;
import java.util.Map;

/**
 * Interface used by {@link PreviewLogger} to store the preview data.
 */
public interface PreviewStore {

  /**
   * Add the preview data.
   * @param rowKey
   * @param columnKey
   * @param value the value to be added
   */
  void add(String rowKey, String columnKey, Object value);

  /**
   * Get the preview data associated with the given program id.
   * @param rowKey
   * @return the {@link Map} of property and associated values logged for the program
   */
  Map<String, List<Object>> get(String rowKey);

  /**
   * Removes the preview data logged by specified programId
   * @param rowKey
   */
  void remove(String rowKey);

  /**
   * Clears the preview data store.
   */
  void clear();
}
