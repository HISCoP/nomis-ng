import React from 'react';

const Dashboard = React.lazy(() => import('./views/dashboard/Dashboard'));
const houseHoldAssessment = React.lazy(() => import('./views/household/household/houseHoldAssessment'));
const HouseholdHomePage = React.lazy(() => import('./views/household/household/HouseholdHomePage'));
const HouseholdMemberHomePage = React.lazy(() => import('./views/household/member/HomePage'));

const routes = [
  { path: '/', exact: true, name: 'Home' },
  { path: '/dashboard', name: 'Dashboard', component: Dashboard },
  { path: '/household/household', name: 'Household Members', component: houseHoldAssessment },
  { path: '/household/home', name: 'Household Home', component: HouseholdHomePage },
  { path: '/household-member/home', name: 'Household Member', component: HouseholdMemberHomePage },
  
];

export default routes;
