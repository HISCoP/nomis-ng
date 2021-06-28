import React from 'react';
import {Header, Menu, Icon} from 'semantic-ui-react'
import 'semantic-ui-css/semantic.min.css'
import {CCol, CDropdown, CDropdownItem, CDropdownMenu, CDropdownToggle, CRow, CWidgetDropdown, CTabContent,
    CTabPane} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import DashboardIcon from '@material-ui/icons/Dashboard';
import GroupIcon from '@material-ui/icons/Group';
import DescriptionIcon from '@material-ui/icons/Description';
import FolderIcon from '@material-ui/icons/Folder';
import HouseholdDashboard from './HouseholdDashboard'
import HouseholdMember from "./HouseholdMember";
import HouseholdService from "./HouseholdService";
import CarePlan from "./CarePlan";
import { makeStyles } from "@material-ui/core/styles";
import AssessmentCarePlanHome from "./AssessmentCarePlanHome";

const useStyles = makeStyles({
    root: {
        position: "sticky",
        top: "1rem",
        minWidth: "275"
    }
});
const HouseholdHomePage = (props) => {
    const classes = useStyles();
    const [activeItem, setActiveItem] = React.useState(props.location.state ? props.location.state : 'dashboard');
    const handleItemClick = (e, { name }) => {
        setActiveItem(name);
    }
    return (
        <>
            <CRow>

                <CCol sm="3" lg="3">
            <Menu className={classes.root} vertical fluid inverted style={{backgroundColor:'#021f54'}}>
                <Menu.Item header className={'p-4'}>
                    <HouseHoldInfo/>
                </Menu.Item>
                <Menu.Item
                    name='dashboard'
                    active={activeItem === 'dashboard'}
                    onClick={handleItemClick}
                    className={'text-center'}
                >
                    <DashboardIcon fontSize="large" className={'text-center'}/>
                    <p>Dashboard</p>
                </Menu.Item>

                <Menu.Item
                    name='members'
                    active={activeItem === 'members'}
                    onClick={handleItemClick}
                    className={'text-center'}
                >
                    <GroupIcon fontSize="large" className={'text-center'}/>
                    <p>Members</p>
                </Menu.Item>

                <Menu.Item
                    name='services'
                    active={activeItem === 'services'}
                    onClick={handleItemClick}
                    className={'text-center'}
                >
                  <DescriptionIcon fontSize="large" className={'text-center'}/>
                       <p>Services</p>

                </Menu.Item>
                <Menu.Item
                    name='careplan'
                    active={activeItem === 'careplan'}
                    onClick={handleItemClick}
                    className={'text-center'}
                >
                    <FolderIcon fontSize="large" className={'text-center'}/>
                    <p>Assessment & Care Plans</p>
                </Menu.Item>
            </Menu>
                </CCol>


                <CCol sm="9" lg="9">
                    <CTabContent>
                        <CTabPane active={activeItem === 'dashboard'} >
                            <HouseholdDashboard />
                        </CTabPane>
                        <CTabPane active={activeItem === 'members'} >
                            <HouseholdMember />
                        </CTabPane>
                        <CTabPane active={activeItem === 'careplan'} >
                           <AssessmentCarePlanHome/>
                        </CTabPane>
                        <CTabPane active={activeItem === 'services'} >
                           <HouseholdService />
                        </CTabPane>
                    </CTabContent>

                </CCol>
                </CRow>
            </>
    )
}

const HouseHoldInfo = () => {
    return (
        <>
            <CRow>
                <CCol sm="12" lg="12">
                    <Header as='h3' inverted dividing>
                        <Icon name='home' />  Household
                    </Header>
                    {/*<Header as='h2' icon inverted  textAlign='center' className={'pt-3 pb-3'}>*/}
                    {/*    <Icon name='home'/>*/}
                    {/*</Header>*/}
                    {/*<Divider />*/}
                </CCol>
                <CCol sm="12" lg="12" className={'text-left pt-3'}>
                    <span>Household ID: <small>12/11/2020 </small></span><br/>
                    <span>Address: <small>No 20, Apata Road, Ibadan </small></span><br/>
                    <span>Date Of Assessment: <small>12/11/2020</small> </span><br/>
                    <span>Primary Caregiver Name: <small>Moses Lambo </small></span><br/>
                    <span>Phone: <small>07057787654</small></span><br/>
                    <span>Sex: <small>Male</small></span><br/>
                    <span>Age: <small>52</small></span><br/>
                    <span>Marital Status: <small>Married</small></span><br/>
                    <span>Occupation: <small>Trader</small></span><br/>
                </CCol>
            </CRow>
        </>
    )
}

export default HouseholdHomePage;