import React from 'react';

const Dashboard = React.lazy(() => import('./views/dashboard/Dashboard'));
const houseHoldAssessment = React.lazy(() => import('./views/household/household/houseHoldAssessment'));

const routes = [
  { path: '/', exact: true, name: 'Home' },
  { path: '/dashboard', name: 'Dashboard', component: Dashboard },
  { path: '/household/household', name: 'Household Members', component: houseHoldAssessment },
  
];

export default routes;
