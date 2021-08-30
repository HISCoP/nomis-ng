import React, {useState, useEffect} from 'react';
import {Header, Menu, Icon} from 'semantic-ui-react'
import 'semantic-ui-css/semantic.min.css'
import {CCol, CDropdown, CDropdownItem, CDropdownMenu, CDropdownToggle, CRow, CWidgetDropdown, CTabContent,
    CTabPane} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import DashboardIcon from '@material-ui/icons/Dashboard';
import GroupIcon from '@material-ui/icons/Group';
import DescriptionIcon from '@material-ui/icons/Description';
import FolderIcon from '@material-ui/icons/Folder';
import EditIcon from '@material-ui/icons/Edit';
import VisibilityIcon from '@material-ui/icons/Visibility';
import HouseholdDashboard from './HouseholdDashboard'
import HouseholdMember from "./HouseholdMember";
import HouseholdService from "./HouseholdService";
import {calculateAge} from "./../../../utils/calculateAge";
import { makeStyles } from "@material-ui/core/styles";
import AssessmentCarePlanHome from "./AssessmentCarePlanHome";
import {fetchHouseHoldById} from "./../../../actions/houseHold";
import {connect} from "react-redux";
import LinearProgress from "@material-ui/core/LinearProgress";

const useStyles = makeStyles({
    root: {
        position: "sticky",
        top: "1rem",
        minWidth: "275"
    }
});
const HouseholdHomePage = (props) => {
    //Getting the house Hold details from the props
    console.log(props.location)
    const houseHoldId = props.location.state;
    const [fetchingHousehold, setFetchingHousehold] = useState(true);
    const classes = useStyles();
    const [activeItem, setActiveItem] = React.useState('dashboard');
    const handleItemClick = (e, { name }) => {
        setActiveItem(name);
    }

    React.useEffect(() => {
        setFetchingHousehold(true);
        const onSuccess = () => {
            setFetchingHousehold(false);
        };
        const onError = () => {
            setFetchingHousehold(false);
        };
        props.fetchHouseHoldById(houseHoldId, onSuccess, onError);
    }, [houseHoldId]);

    return (
        <>
            {fetchingHousehold &&
            <LinearProgress color="primary" thickness={5} className={"mb-2"}/>
            }
            <CRow>

                <CCol sm="3" lg="3">
            <Menu className={classes.root} vertical fluid inverted style={{backgroundColor:'#021f54'}}>
                <Menu.Item header className={'p-4'}>
                    <HouseHoldInfo household={props.hh}/>
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
                    <p>Assessments & Care Plans</p>
                </Menu.Item>
            </Menu>
                </CCol>


                <CCol sm="9" lg="9">
                    <CTabContent>
                        <CTabPane active={activeItem === 'dashboard'} >
                            <HouseholdDashboard household={props.hh}/>
                        </CTabPane>
                        <CTabPane active={activeItem === 'members'} >
                            <HouseholdMember houseHoldId={houseHoldId}/>
                        </CTabPane>
                        <CTabPane active={activeItem === 'careplan'} >
                           <AssessmentCarePlanHome/>
                        </CTabPane>
                        <CTabPane active={activeItem === 'services'} >
                           <HouseholdService householdId={houseHoldId}/>
                        </CTabPane>
                    </CTabContent>

                </CCol>
                </CRow>
            </>
    )
}

const HouseHoldInfo = (props) => {

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
                    {props.household && props.household.details ?
                        <>
                    <span>Household ID: <small> {props.household ? props.household.uniqueId : 'Nil'} </small></span><br/>
                    <span>Address: <small>{props.household.details ? props.household.details.street : 'Nil'} </small>
                        <EditIcon titleAccess="Change household address" fontSize="inherit" className={'text-center'}/>
                    {" "}<VisibilityIcon titleAccess="View Full Address" fontSize="inherit" className={'text-center'}/>
                    </span><br/>
                    <span>Date Of Assessment: <small>{props.household.details.assessmentDate || 'Nil'}</small> </span><br/>
                    <span>Primary Caregiver Name: <small>{props.household.details.primaryCareGiver ? props.household.details.primaryCareGiver.lastName + ' ' + props.household.details.primaryCareGiver.firstName: 'Nil' } </small></span><br/>
                    <span>Phone: <small>{props.household.details.primaryCareGiver ? props.household.details.primaryCareGiver.mobilePhoneNumber : 'Nil' }</small></span><br/>
                    <span>Sex: <small>{props.household.details.primaryCareGiver && props.household.details.primaryCareGiver.sex ? props.household.details.primaryCareGiver.sex.display : 'Nil' }</small></span><br/>
                            {props.household.details.primaryCareGiver && props.household.details.primaryCareGiver.dob ?
                    <span>Age: <small>{calculateAge(  props.household.details.primaryCareGiver.dob)} | {props.household.details.primaryCareGiver.dob}</small></span> :
                                <span>Age: <small>Nil</small></span>
                            }<br/>
                    <span>Marital Status: <small>{props.household.details.primaryCareGiver ? props.household.details.primaryCareGiver.maritalStatus : 'Nil' }</small></span><br/>
                    <span>Occupation: <small>{props.household.details.primaryCareGiver && props.household.details.primaryCareGiver.occupation ? props.household.details.primaryCareGiver.occupation : 'Nil' }</small></span><br/>
                    </> : <></>}
                </CCol>
            </CRow>
        </>
    )
}


const mapStateToProps = (state) => {
    return {
        hh: state.houseHold.household
    };
};

const mapActionToProps = {
    fetchHouseHoldById: fetchHouseHoldById,
};

export default connect(mapStateToProps, mapActionToProps)(HouseholdHomePage);
