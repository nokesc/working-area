import React from 'react';
import { Provider } from 'react-redux';
import { Route, Switch } from 'react-router-dom';
import store from '../app/store';
import Landing from './Landing';
import Login from './Login';
import NetworkPlanPage from './NetworkPlanPage';
import Networks from './Networks';
import Vpcs from './Vpcs';


const App = () => {
  return (
    <Provider store={store}>
      <Switch>
        <Route exact path="/login" component={Login} />
        <Route exact path="/" component={Landing} />
        <Route exact path="/networks" component={Networks} />
        <Route exact path="/vpcs" component={Vpcs} />
        <Route exact path="/network-plans/:id" component={NetworkPlanPage} />
      </Switch>
    </Provider>
  );
};

export default App;