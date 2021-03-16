import React from 'react';

const Dashboard = React.lazy(() => import('./views/dashboard/Dashboard'));
const houseHoldAssessment = React.lazy(() => import('./views/household/household/HouseholdSearch'));
const HouseholdHomePage = React.lazy(() => import('./views/household/household/HouseholdHomePage'));
const HouseholdMemberHomePage = React.lazy(() => import('./views/household/member/HomePage'));
const MemberSearch = React.lazy(() => import('./views/household/member/MemberSearch'));

const routes = [
  { path: '/', exact: true, name: 'Home' },
  { path: '/dashboard', name: 'Dashboard', component: Dashboard },
  { path: '/households', name: 'Household Members', component: houseHoldAssessment },
  { path: '/household/home', name: 'Household Home', component: HouseholdHomePage },
  { path: '/household-member/home', name: 'Household Member', component: HouseholdMemberHomePage },
  { path: '/household-members', name: 'Household Members', component: MemberSearch },
  
];

export default routes;
