import React from 'react';
import {Header, Menu, Icon, Dropdown} from 'semantic-ui-react'
import 'semantic-ui-css/semantic.min.css'
import {CCol, CRow, CTabContent,
    CTabPane} from "@coreui/react";
import { Link } from 'react-router-dom'
import DashboardIcon from '@material-ui/icons/Dashboard';
import DescriptionIcon from '@material-ui/icons/Description';
import FolderIcon from '@material-ui/icons/Folder';
import Dashboard from './Dashboard'
import ServiceHomePage from "./ServicePage";
import SettingsIcon from '@material-ui/icons/Settings';
import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles({
    root: {
        position: "sticky",
        top: "1rem",
        minWidth: "275"
    }
});

const HomePage = () => {
    const classes = useStyles();
    const [activeItem, setActiveItem] = React.useState('dashboard');
    const handleItemClick = (e, { name }) => {
        setActiveItem(name);
    }

    return (
        <>
            <CRow>
                <CCol sm="3" lg="3">
                    <Menu className={classes.root} vertical fluid inverted style={{backgroundColor:'#021f54'}}>
                        <Menu.Item header className={'p-4'}>
                            <InfoSection/>
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
                            name='household'
                            active={activeItem === 'household'}
                            className={'text-center'}
                            onClick={()=>{}}
                        >
                            <Link color="inherit" to ={{
                                pathname: "/household/home",
                            }}  >
                                <FolderIcon fontSize="large" className={'text-center'}/>
                                <p>Household</p>
                            </Link>
                        </Menu.Item>
                        <Menu.Item
                            name='more'
                            active={activeItem === 'more'}
                            className={'text-center'}
                            onClick={()=>{}}
                        >
                            <SettingsIcon fontSize="large" className={'text-center'}/><br />
                            <span>More</span>
                            <Dropdown>
                                <Dropdown.Menu>
                                    <Dropdown.Item icon='edit' text='Edit Profile' />
                                    <Dropdown.Item icon='globe' text='Choose Language' />
                                    <Dropdown.Item icon='settings' text='Account Settings' />
                                </Dropdown.Menu>
                            </Dropdown>
                        </Menu.Item>
                    </Menu>

                </CCol>


                <CCol sm="9" lg="9">
                    <CTabContent>
                        <CTabPane active={activeItem === 'dashboard'} >
                            <Dashboard />
                        </CTabPane>
                        <CTabPane active={activeItem === 'services'} >
                            <ServiceHomePage />
                        </CTabPane>

                    </CTabContent>

                </CCol>
            </CRow>
        </>
    )
}

const InfoSection = () => {
    return (
        <>
            <CRow>
                <CCol sm="12" lg="12">
                    <Header as='h3' inverted dividing>
                        <Icon name='user' />  Member
                    </Header>
                </CCol>
                <CCol sm="12" lg="12" className={'text-left pt-3'}>
                    <span>Unique ID: <small>12/11/2020 </small></span><br/>
                    <span>Name: <small>Moses Lambo </small></span><br/>
                    <span>Phone: <small>07057787654</small></span><br/>
                    <span>Sex: <small>Male</small></span> {'  '}
                    <span>Age: <small>52</small></span><br/>
                    <span>Date Of Assessment: <small>12/11/2020</small> </span><br/><br/>

                    <span>State: <small>Abia</small></span><br/>
                    <span>LGA: <small>Okelewo</small></span><br/>
                    <span>CBO: <small>MSM</small></span><br/>
                </CCol>
            </CRow>
        </>
    )
}

export default HomePage;