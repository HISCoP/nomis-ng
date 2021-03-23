import React from 'react';

const Dashboard = React.lazy(() => import('./views/dashboard/Dashboard'));
const HouseHoldAssessment = React.lazy(() => import('./views/household/household/houseHoldAssessment'));
const HouseholdHomePage = React.lazy(() => import('./views/household/household/HouseholdHomePage'));
const HouseholdMemberHomePage = React.lazy(() => import('./views/household/member/HomePage'));

/* Admin */
const AdminHomePage = React.lazy(() => import('./views/admin/HomePage'));

/* REPORT */
const ReportPage = React.lazy(() => import('./views/report/Index'));
const routes = [
  { path: '/', exact: true, name: 'Home' },
  { path: '/dashboard', name: 'Dashboard', component: Dashboard },
  { path: '/household/household', name: 'Household Members', component: HouseHoldAssessment },
  { path: '/household/home', name: 'Household Home', component: HouseholdHomePage },
  { path: '/household-member/home', name: 'Household Member', component: HouseholdMemberHomePage },
  { path: '/admin', name: 'Admin Dashboard', component: AdminHomePage },
  { path: '/report', name: 'Report Page', component: ReportPage },
  
];

export default routes;
