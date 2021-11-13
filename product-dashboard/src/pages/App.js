import React from 'react';
import { Switch, Route } from 'react-router-dom';

import Landing from './Landing';
import Networks from './Networks';
import Vpcs from './Vpcs';


class App extends React.Component {
    render() {
        return  (
            <Switch>
                <Route exact path="/" component={Landing} />
                <Route exact path="/networks" component={Networks} />
                <Route exact path="/vpcs" component={Vpcs} />
            </Switch>
        );
    };
};

export default App;