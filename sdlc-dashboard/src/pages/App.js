import React, { useState, useEffect } from 'react';
import { ipv4NetworkReferenceData_prefixLengths } from '../components/networking/InfraServiceAPI';
import { Switch, Route } from 'react-router-dom';

import Landing from './Landing';
import Networks from './Networks';
import NetworkPlanPage from './NetworkPlanPage';
import Vpcs from './Vpcs';
import Login from './Login';
import { ipv4NetworkReferenceData_privateCidrBlocks } from '../components/networking/InfraServiceAPI';

const App = () => {

    const [prefixLengths, setPrefixLengths] = useState([]);
    useEffect(() => {
      ipv4NetworkReferenceData_prefixLengths().then((response) => {
        console.log("-> App.useEffect (prefixLengths)");
        setPrefixLengths(response.data);
      });
    }, []);

    return (
        <Switch>
            <Route exact path="/login" component={Login} />
            <Route exact path="/" component={Landing} />
            <Route exact path="/networks" component={Networks} />
            <Route exact path="/vpcs" component={Vpcs} />
            <Route exact path="/network-plans/:id" component={NetworkPlanPage} />
        </Switch>
    );
};

export default App;