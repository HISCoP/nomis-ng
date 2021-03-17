import React, {createRef, useState} from 'react';
import {Header, Menu, Icon, Dropdown, Input} from 'semantic-ui-react'
import 'semantic-ui-css/semantic.min.css'
import {CCol, CRow, CTabContent,
    CTabPane} from "@coreui/react";
import { Link } from 'react-router-dom'
import DashboardIcon from '@material-ui/icons/Dashboard';
import DescriptionIcon from '@material-ui/icons/Description';
import FolderIcon from '@material-ui/icons/Folder';
import People from '@material-ui/icons/People';
import Dashboard from './Dashboard'
import ServicePage from "./ServicePage";
import SettingsIcon from '@material-ui/icons/Settings';



const HomePage = () => {
    let contextRef = createRef()
    const [activeItem, setActiveItem] = React.useState('dashboard');
    const handleItemClick = (e, { name }) => {
        setActiveItem(name);
    }
    const openHouseHoldPage = () => {
        window.location.href = "/household/home";
    }

   const [state, setState ]=useState({}) 

   
    
    return (
        <>
            <CRow>

                <CCol sm="3" lg="3">
                    {/*className={'bg-success'}*/}
             <Menu vertical fluid inverted style={{backgroundColor:'#021f54'}}>
                <Menu.Item header className={'p-4'}>
                    <InfoSection/>
                </Menu.Item>
                {/* <Menu.Item
                    name='dashboard'
                    active={activeItem === 'dashboard'}
                    onClick={handleItemClick}
                    className={'text-center'}
                >

                    <DashboardIcon fontSize="large" className={'text-center'}/>
                    <p>Dashboard</p>
                </Menu.Item> */}

                {/* <Menu.Item
                    name='services'
                    active={activeItem === 'services'}
                    onClick={handleItemClick}
                    className={'text-center'}
                >
                  <People fontSize="large" className={'text-center'}/>
                       <p>Users</p>

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
                    <DescriptionIcon fontSize="large" className={'text-center'}/>
                    <p>Form Builder</p>
                    </Link>
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
                    <p>Report Builder</p>
                    </Link>
                </Menu.Item>
                <Menu.Item
                    name='more'
                    active={activeItem === 'more'}
                    className={'text-center'}
                    onClick={()=>{}}
                >
                   <SettingsIcon fontSize="large" className={'text-center'}/><br />
                    <span>Setting</span>
                <Dropdown>
                    <Dropdown.Menu>
                        <Dropdown.Item icon='edit' text='Edit Profile' />
                        <Dropdown.Item icon='globe' text='Choose Language' />
                        <Dropdown.Item icon='settings' text='Account Settings' />
                    </Dropdown.Menu>
                </Dropdown>
                </Menu.Item> */}
        <Menu.Item
          name='messages'
          active={activeItem === 'messages'}
          onClick={handleItemClick}
        >
       <DashboardIcon className={'text-left'}/>
                  <span className={'pl-2'}>  Dashboard </span>
        </Menu.Item>
        <Menu.Item
          name='messages'
          active={activeItem === 'messages'}
          onClick={handleItemClick}
        >
        <People fontSize="large" className={'text-left'}/>
        <span className={'pl-2'}>  User Setup </span>
        </Menu.Item>
        <Menu.Item
          name='messages'
          active={activeItem === 'messages'}
          onClick={handleItemClick}
        >
         <DescriptionIcon fontSize="large" className={'text-left'}/>
         <span className={'pl-2'}>  Form Builder </span>
        </Menu.Item>
        <Menu.Item
          
        >
        <FolderIcon fontSize="large" className={'text-center'}/>
        <span className={'pl-2'}>  Report Builder  </span>
        </Menu.Item>
    
      

        <Dropdown item text='Setting' >    
        
        <Dropdown.Menu>
            <Dropdown.Item icon='edit' text='OVS' />
            <Dropdown.Item icon='globe' text='Application Code Setup' />
            <Dropdown.Item icon='settings' text='Facilities Setup' />
          </Dropdown.Menu>
        </Dropdown>
            </Menu>

                </CCol>


                <CCol sm="9" lg="9">
                    <CTabContent>
                        <CTabPane active={activeItem === 'dashboard'} >
                            <Dashboard />
                        </CTabPane>
                        <CTabPane active={activeItem === 'services'} >
                            <ServicePage />
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
                    {/* <Header as='h3' inverted dividing>
                        <Icon name='user' />  Member
                    </Header> */}
                </CCol>
                {/* <CCol sm="12" lg="12" className={'text-left pt-3'}>
                    <span>Unique ID: <small>12/11/2020 </small></span><br/>
                    <span>Name: <small>Moses Lambo </small></span><br/>
                    <span>Phone: <small>07057787654</small></span><br/>
                    <span>Sex: <small>Male</small></span> {'  '}
                    <span>Age: <small>52</small></span><br/>
                    <span>Date Of Assessment: <small>12/11/2020</small> </span><br/><br/>

                    <span>State: <small>Abia</small></span><br/>
                    <span>LGA: <small>Okelewo</small></span><br/>
                    <span>CBO: <small>MSM</small></span><br/>
                </CCol> */}
            </CRow>
        </>
    )
}

export default HomePage;