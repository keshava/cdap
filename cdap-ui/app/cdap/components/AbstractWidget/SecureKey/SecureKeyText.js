/*
 * Copyright © 2019 Cask Data, Inc.
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

import React from 'react';
import SecureKey from 'components/AbstractWidget/SecureKey';
import { DEFAULT_WIDGET_PROPS } from 'components/AbstractWidget';
import { WIDGET_PROPS } from 'components/AbstractWidget/constants';

export default function SecureKeyText(props) {
  return <SecureKey inputTextType="text" {...props} />;
}

SecureKeyText.propTypes = WIDGET_PROPS;
SecureKeyText.defaultProps = DEFAULT_WIDGET_PROPS;
