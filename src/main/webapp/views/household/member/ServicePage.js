import React from "react";
import { CCol, CRow, CButton, CCard,
    CCardBody,
    CLink,
    CCardHeader, CCardFooter} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import {Button, List, Container, Divider, Header, Icon} from 'semantic-ui-react'
import AccountCircleIcon from '@material-ui/icons/AccountCircle';

const HouseholdService = () => {
    return (
        <>
            <CRow>
                <CCol xs="12">

                <Icon name='users' />  Household Services
           <hr />
                </CCol>
            </CRow>
            <CRow>
                    <CCol xs="12" >
                        <CCard>
                            <CCardHeader>Recent Service Forms
                                {/*<div className="card-header-actions">*/}
                                {/*    <CLink className="card-header-action">*/}
                                {/*        <CIcon name="cil-list" /> View All*/}
                                {/*    </CLink>*/}
                                {/*</div>*/}
                            </CCardHeader>
                            <CCardBody>
                                <List divided verticalAlign='middle'>
                                    <List.Item>
                                        <List.Content floated='right'>
                                            <Button>View</Button>
                                        </List.Content>
                                        <List.Content>Household Vunerabilty Assessment - Ada Kindu</List.Content>
                                        <List.Description>29/10/2020 15:09 PM </List.Description>
                                    </List.Item>
                                    <List.Item>
                                        <List.Content floated='right'>
                                            <Button>View</Button>
                                        </List.Content>
                                        <List.Content>Caregiver Form - Amos Kindu</List.Content>
                                        <List.Description>29/10/2020 15:09 PM </List.Description>
                                    </List.Item>

                                    <List.Item>
                                        <List.Content floated='right'>
                                            <Button>View</Button>
                                        </List.Content>
                                        <List.Content>Household Followup Assessment - Amos Kindu</List.Content>
                                        <List.Description>29/10/2020 15:09 PM </List.Description>
                                    </List.Item>
                                <List.Item>
                                    <List.Content floated='right'>
                                        <Button>View</Button>
                                    </List.Content>
                                    <List.Content>Caregiver Form - Amos Kindu</List.Content>
                                    <List.Description>29/10/2020 15:09 PM </List.Description>
                                </List.Item>

                                <List.Item>
                                    <List.Content floated='right'>
                                        <Button>View</Button>
                                    </List.Content>
                                    <List.Content>Household Followup Assessment - Amos Kindu</List.Content>
                                    <List.Description>29/10/2020 15:09 PM </List.Description>
                                </List.Item>
                            </List>
                            </CCardBody>
                        </CCard>
                    </CCol>
            </CRow>
            </>
    );
}


export default HouseholdService;