import React from 'react';
import { CCard,
    CCardBody,
    CLink,
    CCardHeader,} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import {Button, List} from 'semantic-ui-react'
import MaterialTable from 'material-table';

const ServiceHistoryPage = (props) => {
    //should be able to fetch by either houseHoldId or memberId
    const houseHoldId = props.houseHoldId;
    const memberId = props.memberId;
    const isBoolean = (variable) => typeof variable === "boolean";
    const isHistory = isBoolean(props.isHistory) ? props.isHistory : true;

    if(isHistory) {
    return (
     <CCard>
                    <CCardHeader>Recent Service Forms

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
    );
    }

    return (

 <MaterialTable
                title="Services Form History"
                columns={[
                    { title: 'Form Name', field: 'name' },
                    { title: 'Date', field: 'date' },
                      { title: 'Name', field: 'surname', hidden: memberId ? true:false },
                ]}
                data={[
                    { name: 'Household Vunerabilty Assessment', surname: 'Ama Kindu', date: '12/11/2020 08:35 AM', birthCity: 63 },
                    { name: 'Household Followup Assessment', surname: 'Nisa Baran', date: '12/11/2020 08:35 AM', birthCity: 34 },
                ]}
                actions={[
                    {
                        icon: 'edit',
                        tooltip: 'View Form',
                        onClick: (event, rowData) => alert("You saved " + rowData.name)
                    },
                    rowData => ({
                        icon: 'visibility',
                        tooltip: 'View Form',
                        onClick: (event, rowData) => alert("You want to delete " + rowData.name)

                    })
                ]}
                options={{
                    actionsColumnIndex: -1,
                    padding: 'dense',
                    header: false
                }}
            />
                        );
}

export default ServiceHistoryPage;