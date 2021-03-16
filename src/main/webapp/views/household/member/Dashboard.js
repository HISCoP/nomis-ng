import React from "react";
import { CCol, CRow, CWidgetIcon, CCard,
    CCardBody,
    CLink,
    CCardHeader,} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import { Button, List, Container, Divider } from 'semantic-ui-react'

const HouseholdDashboard = () => {

    return (
        <>
                <CRow>
                    <CCol xs="12" sm="6" lg="4">
                <CWidgetIcon text="Total Services" header="21,345"  color="success" iconPadding={false}>
                    <CIcon width={24} name="cil-notes"/>
                </CWidgetIcon>
                    </CCol>
                    <CCol xs="12" sm="6" lg="4">
                        <CWidgetIcon text="Total People" header="10" color="success"  iconPadding={false}>
                            <CIcon width={24} name="cil-people"/>
                        </CWidgetIcon>
                    </CCol>
                        <CCol xs="12" sm="6" lg="4">
                        <CWidgetIcon text="Total OVC" header="2" color="success" iconPadding={false}>
                            <CIcon width={24} name="cil-user"/>
                        </CWidgetIcon>
                        </CCol>
                </CRow>
        <CRow>
            <CCol xs="12" >
            <CCard>
                <CCardHeader>Recent Service Forms
                    <div className="card-header-actions">
                        <CLink className="card-header-action">
                            <CIcon name="cil-list" /> View All
                        </CLink>
                    </div>
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
                </List>
                </CCardBody>
            </CCard>
            </CCol>

            <CCol xs="12" >
                <CCard>
                    <CCardHeader>OVCs
                        <div className="card-header-actions">
                            <CLink className="card-header-action">
                                <CIcon name="cil-list" /> View All
                            </CLink>
                        </div>
                    </CCardHeader>

                    <CCardBody>
                    <List divided verticalAlign='middle'>
                        <List.Item>
                            <List.Content floated='right'>
                                <Button>View</Button>
                            </List.Content>
                            <List.Content>Amos Kindu - Female</List.Content>
                            <List.Description>12 years </List.Description>
                        </List.Item>
                        <List.Item>
                            <List.Content floated='right'>
                                <Button>View</Button>
                            </List.Content>
                            <List.Content>Ada Kindu - Male</List.Content>
                            <List.Description>5 years</List.Description>
                        </List.Item>
                        <List.Item>
                            <List.Content floated='right'>
                                <Button>View</Button>
                            </List.Content>
                            <List.Content>Soma Kindu</List.Content>
                            <List.Description>3 years</List.Description>
                        </List.Item>
                    </List>
                    </CCardBody>
                </CCard>
            </CCol>
    </CRow>
    </>

    )
}

export default HouseholdDashboard;