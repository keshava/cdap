/*
 * Copyright © 2020 Cask Data, Inc.
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
import withStyles, { WithStyles, StyleRules } from '@material-ui/core/styles/withStyles';
import LeftPanel from 'components/Replicator/Create/LeftPanel';
import EntityTopPanel from 'components/EntityTopPanel';
import Content from 'components/Replicator/Create/Content';

export const CreateContext = React.createContext({});

const styles = (): StyleRules => {
  return {
    root: {
      height: '100%',
    },
    content: {
      height: 'calc(100% - 50px)',
      display: 'grid',
      gridTemplateColumns: '250px 1fr',
    },
  };
};

interface ICreateState {
  name: string;
  description: string;
  sourcePlugin: any;
  targetPlugin: any;
  sourceConfig: any;
  targetConfig: any;
  activeStep: number;
  setActiveStep: (step: number) => void;
}

export type ICreateContext = Partial<ICreateState>;

class CreateView extends React.PureComponent<WithStyles<typeof styles>, ICreateContext> {
  public setActiveStep = (step: number) => {
    this.setState({ activeStep: step });
  };

  public state = {
    name: '',
    description: '',
    sourcePlugin: null,
    targetPlugin: null,
    sourceConfig: null,
    targetConfig: null,

    activeStep: 0,

    setActiveStep: this.setActiveStep,
  };

  public render() {
    return (
      <CreateContext.Provider value={this.state}>
        <div className={this.props.classes.root}>
          <EntityTopPanel title="Create new Replicator" closeBtnAnchorLink={() => history.back()} />
          <div className={this.props.classes.content}>
            <LeftPanel />
            <Content />
          </div>
        </div>
      </CreateContext.Provider>
    );
  }
}

export function createContextConnect(Comp) {
  return (extraProps) => {
    return (
      <CreateContext.Consumer>
        {(props) => {
          const finalProps = {
            ...props,
            ...extraProps,
          };

          return <Comp {...finalProps} />;
        }}
      </CreateContext.Consumer>
    );
  };
}

const Create = withStyles(styles)(CreateView);
export default Create;
