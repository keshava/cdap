/*
 * Copyright © 2018 Cask Data, Inc.
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

import * as React from 'react';
import T from 'i18n-react';
import { connect } from 'react-redux';
import { IPipeline } from 'components/PipelineList/DeployedPipelineView/types';

import './PipelineCount.scss';

interface IProps {
  pipelines: IPipeline[];
  pipelinesLoading: boolean;
}

const PREFIX = 'features.PipelineList';

const PipelineCountView: React.SFC<IProps> = ({ pipelines, pipelinesLoading }) => {
  if (pipelinesLoading) {
    return null;
  }
  return (
    <div className="pipeline-count">
      <h5>
        {T.translate(`${PREFIX}.DeployedPipelineView.pipelineCount`, {
          context: pipelines.length,
        })}
      </h5>
    </div>
  );
};

const mapStateToProps = (state) => {
  return {
    pipelines: state.deployed.pipelines,
    pipelinesLoading: state.deployed.pipelinesLoading,
  };
};

const PipelineCount = connect(mapStateToProps)(PipelineCountView);

export default PipelineCount;