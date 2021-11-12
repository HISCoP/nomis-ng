import React, {useState} from 'react';
import {Header, Menu, Icon} from 'semantic-ui-react'
import 'semantic-ui-css/semantic.min.css'
import {CCol, CRow, CTabContent,
    CTabPane} from "@coreui/react";
import { Link } from 'react-router-dom'
import DashboardIcon from '@material-ui/icons/Dashboard';
import DescriptionIcon from '@material-ui/icons/Description';
import FolderIcon from '@material-ui/icons/Folder';
import HomeIcon from '@material-ui/icons/Home';
import ListIcon from '@material-ui/icons/List';
import Dashboard from './Dashboard'
import ServiceHomePage from "./ServiceHistoryPage";
import Forms from "./FillForms";
import { makeStyles } from "@material-ui/core/styles";
import LinearProgress from "@material-ui/core/LinearProgress";
import {fetchHouseHoldMemberById} from "../../../actions/houseHoldMember";
import {connect} from "react-redux";
import {calculateAge} from "./../../../utils/calculateAge";
import {fetchHouseHoldById} from "../../../actions/houseHold";
import ProvideService from "../household/ProvideService";

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
    const [showServiceModal, setShowServiceModal] = useState(false);
    const toggleServiceModal = () => setShowServiceModal(!showServiceModal);
    const handleItemClick = (e, { name }) => {
        setActiveItem(name);
    }
    const reloadPage = () => {
        let item = activeItem;
        setActiveItem("");
        setActiveItem(activeItem);
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
                    <Menu className={classes.root} vertical fluid inverted style={{backgroundColor:'#096150'}}>
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
                            name='provide_services'
                            active={activeItem === 'provide_services'}
                            onClick={toggleServiceModal}
                            className={'text-center'}
                        >
                            <DescriptionIcon fontSize="large" className={'text-center'}/>
                            <p>Provide Service</p>

                        </Menu.Item>

                        <Menu.Item
                            name='forms'
                            active={activeItem === 'forms'}
                            className={'text-center'}
                            onClick={handleItemClick}
                        >
                           
                                <FolderIcon fontSize="large" className={'text-center'}/>
                                <p>Other Forms</p>
                          
                        </Menu.Item>
                        <Menu.Item
                            name='services'
                            active={activeItem === 'services'}
                            onClick={handleItemClick}
                            className={'text-center'}
                        >
                            <ListIcon fontSize="large" className={'text-center'}/>
                            <p>Form History</p>

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
                                <HomeIcon fontSize="large" className={'text-center'}/>
                                <p>Household</p>
                            </Link>
                        </Menu.Item>
                        
                        
                    </Menu>

                </CCol>


                <CCol sm="9" lg="9">
                    <CTabContent>
                        <CTabPane active={activeItem === 'dashboard'} >
                            {activeItem === "dashboard" &&
                            <Dashboard member={props.member} household={props.hh} fetchingHousehold={fetchingHousehold}
                                       />
                            }
                        </CTabPane>
                        <CTabPane active={activeItem === 'services'} >
                            {activeItem === "services" &&
                            <ServiceHomePage member={props.member} />
                            }
                        </CTabPane>
                        <CTabPane active={activeItem === 'forms'} >
                            {activeItem === "forms" &&
                            <Forms member={props.member} />
                            }
                        </CTabPane>

                    </CTabContent>

                </CCol>
            </CRow>

            <ProvideService  modal={showServiceModal} toggle={toggleServiceModal} memberId={props.member.id} reloadSearch={reloadPage} />
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
                    <span>Sex: <small>{props.member && props.member.sex && props.member.sex.display ? props.member.sex.display : (props.member.sex === 2 ? "Male" : "Female")}</small></span> {'  '}
                        {props.member.dob ?
                    <span>Age:  <small>{calculateAge(  props.member.dob)} | {props.member.dob}</small></span> :
                        <span>Age: <small>Nil</small></span>
                        }<br/>
                    <span>Date Of Assessment: <small>{props.member ? props.member.dateOfEnrolment : 'Nil'}</small> </span><br/><br/>

                    {/*<span>State: <small>{props.member ? props.member.state : ""}</small></span><br/>*/}
                    {/*<span>LGA: <small>-</small></span><br/>*/}
                    {/*<span>CBO: <small>-</small></span><br/>*/}
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