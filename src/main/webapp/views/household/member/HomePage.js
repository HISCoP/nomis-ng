import React, {useState} from 'react';
import {Header, Menu, Icon} from 'semantic-ui-react'
import 'semantic-ui-css/semantic.min.css'
import {CCol, CRow, CTabContent,
    CTabPane} from "@coreui/react";
import { Link } from 'react-router-dom'
import DashboardIcon from '@material-ui/icons/Dashboard';
import DescriptionIcon from '@material-ui/icons/Description';
import FolderIcon from '@material-ui/icons/Folder';
import Dashboard from './Dashboard'
import ServiceHomePage from "./ServiceHistoryPage";
import Forms from "./Forms";
import { makeStyles } from "@material-ui/core/styles";
import LinearProgress from "@material-ui/core/LinearProgress";
import {fetchHouseHoldMemberById} from "../../../actions/houseHoldMember";
import {connect} from "react-redux";
import {calculateAge} from "./../../../utils/calculateAge";
import {fetchHouseHoldById} from "../../../actions/houseHold";

const useStyles = makeStyles({
    root: {
        position: "sticky",
        top: "1rem",
        minWidth: "275"
    }
});

const HomePage = (props) => {
    console.log(props)
    const classes = useStyles();
    const memberId = props.location.state;
    const householdId = props.location.householdId;
    const [fetchingMember, setFetchingMember] = useState(true);
    const [fetchingHousehold, setFetchingHousehold] = useState(true);
    const [activeItem, setActiveItem] = React.useState('dashboard');
    const handleItemClick = (e, { name }) => {
        setActiveItem(name);
    }

    React.useEffect(() => {
        setFetchingMember(true);
        const onSuccess = () => {
            setFetchingMember(false);
        };
        const onError = () => {
            setFetchingMember(false);
        };
        props.fetchHouseHoldMemberById(memberId, onSuccess, onError);
    }, [memberId]);
    React.useEffect(() => {
        setFetchingHousehold(true);
        const onSuccess = () => {
            setFetchingHousehold(false);
        };
        const onError = () => {
            setFetchingHousehold(false);
        };
        props.fetchHouseHoldById(householdId, onSuccess, onError);
    }, [householdId]);

    return (
        <>
            {fetchingMember &&
            <LinearProgress color="primary" thickness={5} className={"mb-2"}/>
            }
            <CRow>
                <CCol sm="3" lg="3">
                    <Menu className={classes.root} vertical fluid inverted style={{backgroundColor:'#021f54'}}>
                        <Menu.Item header className={'p-4'}>
                            <InfoSection member={props.member && props.member.details ? props.member.details : props.member} householdMemberType={props.member ? props.member.householdMemberType : ''}/>
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
                            name='services'
                            active={activeItem === 'services'}
                            onClick={handleItemClick}
                            className={'text-center'}
                        >
                            <DescriptionIcon fontSize="large" className={'text-center'}/>
                            <p>Services</p>

                        </Menu.Item>
                        <Menu.Item
                            name='forms'
                            active={activeItem === 'forms'}
                            className={'text-center'}
                            onClick={handleItemClick}
                        >
                           
                                <FolderIcon fontSize="large" className={'text-center'}/>
                                <p>Forms</p>
                          
                        </Menu.Item>
                        <Menu.Item
                            name='household'
                            active={activeItem === 'household'}
                            className={'text-center'}
                            onClick={()=>{}}
                        >
                            <Link color="inherit" to ={{
                                pathname: "/household/home",
                                state:householdId
                            }}  >
                                <FolderIcon fontSize="large" className={'text-center'}/>
                                <p>Household</p>
                            </Link>
                        </Menu.Item>
                        
                        
                    </Menu>

                </CCol>


                <CCol sm="9" lg="9">
                    <CTabContent>
                        <CTabPane active={activeItem === 'dashboard'} >
                            <Dashboard member={props.member} household={props.hh} fetchingHousehold={fetchingHousehold} />
                        </CTabPane>
                        <CTabPane active={activeItem === 'services'} >
                            <ServiceHomePage />
                        </CTabPane>
                        <CTabPane active={activeItem === 'forms'} >
                            <Forms />
                        </CTabPane>

                    </CTabContent>

                </CCol>
            </CRow>
        </>
    )
}

const InfoSection = (props) => {
    return (
        <>
            <CRow>
                <CCol sm="12" lg="12">
                    <Header as='h3' inverted dividing>
                        <Icon name='user' />  Member - {props.householdMemberType === 1?"Caregiver": "OVC" }
                    </Header>
                </CCol>
                <CCol sm="12" lg="12" className={'text-left pt-3'}>
                    {!props.member ? <></> :
                    <>
                    <span>Unique ID: <small>{props.member ? props.member.uniqueId : 'Nil'} </small></span><br/>
                    <span>Name: <small>{props.member ? props.member.firstName + ' ' + props.member.lastName : 'Nil'} </small></span><br/>
                    <span>Phone: <small>{props.member ? props.member.mobilePhoneNumber : 'Nil'}</small></span><br/>
                    <span>Sex: <small>{props.member && props.member.sex ? props.member.sex.display : 'Nil'}</small></span> {'  '}
                        {props.member.dob ?
                    <span>Age:  <small>{calculateAge(  props.member.dob)} | {props.member.dob}</small></span> :
                        <span>Age: <small>Nil</small></span>
                        }<br/>
                    <span>Date Of Assessment: <small>{props.member ? props.member.dateAssessment : 'Nil'}</small> </span><br/><br/>

                    <span>State: <small>-</small></span><br/>
                    <span>LGA: <small>-</small></span><br/>
                    <span>CBO: <small>-</small></span><br/>
                    </>}
                </CCol>
            </CRow>
        </>
    )
}

const mapStateToProps = (state) => {
    return {
        member: state.houseHoldMember.member,
        hh: state.houseHold.household
    };
};

const mapActionToProps = {
    fetchHouseHoldMemberById: fetchHouseHoldMemberById,
    fetchHouseHoldById: fetchHouseHoldById,
};

export default connect(mapStateToProps, mapActionToProps)(HomePage);