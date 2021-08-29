import React, {createRef, useState} from 'react';
import {Header, Menu, Icon, Dropdown, Input} from 'semantic-ui-react'
import 'semantic-ui-css/semantic.min.css'
import {CCol, CRow, CTabContent, CTabPane, } from "@coreui/react";
import { Link } from 'react-router-dom'
import DashboardIcon from '@material-ui/icons/Dashboard';
import ListIcon from '@material-ui/icons/List';
import DnsIcon from '@material-ui/icons/Dns';
import People from '@material-ui/icons/People';
import AcUnitIcon from '@material-ui/icons/AcUnit';
import CropFreeIcon from '@material-ui/icons/CropFree';
import DomainIcon from '@material-ui/icons/Domain';
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

        <Menu.Item
          name='messages'
          active={activeItem === 'dashboard'}
          onClick={handleItemClick}>

       <DashboardIcon className={'text-left'}/>
                  <span className={'pl-2'}>  Dashboard </span>
        </Menu.Item>
        <Menu.Item
          name='messages'
          active={activeItem === 'usersetup'}
          
        >
         <Link color="inherit" to ={{ pathname: "user-setup-home", }}  >
            <People fontSize="small" className={'text-left'}/>
            <span className={'pl-2'}>  User Setup </span>
        </Link>
        </Menu.Item>
        <Menu.Item
          name='messages'
          active={activeItem === 'usersetup'}>
        <Link color="inherit" to ={{ pathname: "organisation-unit-home", }}  >
            <AcUnitIcon fontSize="small" className={'text-left'}/>
            <span className={'pl-2'}>  Organisation Unit </span>
        </Link>
        </Menu.Item>
        <Menu.Item
          name='messages'
          active={activeItem === 'usersetup'}
        >
        <Link color="inherit" to ={{ pathname: "application-codeset-home", }}  >
            <CropFreeIcon fontSize="small" className={'text-left'}/>
            <span className={'pl-2'}> Application Codeset </span>
            </Link>
        </Menu.Item>
        <Menu.Item
          name='messages'
          active={activeItem === 'usersetup'}
        
        >
         <Link color="inherit" to ={{ pathname: "program-setup-home", }}  >
            <DomainIcon fontSize="small" className={'text-left'}/>
            <span className={'pl-2'}>  Domain Setup </span>
        </Link>
        </Menu.Item>
        <Menu.Item
          name='messages'
          active={activeItem === 'formbuilder'}
          onClick={()=>{}}>
            <Link color="inherit" to ={{pathname: "form-home",}}  >
                <DnsIcon fontSize="small" className={'text-left'}/>
                <span className={'pl-2'}>  Form Builder </span>
            </Link>

        </Menu.Item>
        <Menu.Item>
        <Link color="inherit" to ={{ pathname: "report-builder-home", }}  >
            <ListIcon fontSize="small" className={'text-left'}/>
            <span className={'pl-2'}>  Report Builder  </span>
        </Link>
        </Menu.Item>


        <Menu.Item>
            <SettingsIcon fontSize="small" className={'text-left'}/>
            <span className={'pl-2'}>Others</span>
        <Dropdown   className={'float-right'} >  
                
        <Dropdown.Menu >
            <Dropdown.Item icon='edit' text='OVS Setting' />
            <Dropdown.Item icon='globe' text='Ward Setup' />
            <Dropdown.Item icon='settings' text='Facilities Setup' />
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