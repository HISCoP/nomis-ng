import React, {useState} from 'react';
import {CButton, CCol, CRow, CCard, CDataTable, CCardBody} from "@coreui/react";
import {Icon} from "semantic-ui-react";
import {CChartBar} from "@coreui/react-chartjs";
import MaterialTable from 'material-table';
import NewCarePlan from './NewCarePlan';

const CarePlan = () => {
    const [modal, setModal] = useState(false);
    const toggle = () => setModal(!modal);
    const fields = ['dateCreated','totalServices', 'pending', 'inProgress', 'completed', 'Actions']
    const usersData = [
        {pending: 0, totalServices: '10', dateCreated: '2018/01/01', inProgress: '3', completed: '7'},
        {pending: 1, totalServices: '7', dateCreated: '2018/01/01', inProgress: '2', completed: '4'},
        {pending: 2, totalServices: '5', dateCreated: '2018/02/01', inProgress: '3', completed: '0'}
];
    return (
        <>
            <CRow>
                <CCol xs="12">

                    <Icon name='users' />  Household Careplan(s)

                    <CButton color={"primary"} className="float-right"> New Care Plan</CButton>
                    <hr />
                </CCol>
            </CRow>

            <CRow>
                <CCol xs="12">
                    <CCard>
                        <CCardBody>
                            <CChartBar
                                datasets={[
                                    {
                                        label: 'Pending',
                                        backgroundColor: '#f87979',
                                        data: [1, 2, 2, 3]
                                    },
                                    {
                                        label: 'In Progress',
                                        backgroundColor: '#f8a121',
                                        data: [2, 3, 4, 2]
                                    },
                                    {
                                        label: 'Completed',
                                        backgroundColor: '#01f83a',
                                        data: [3, 3, 4, 2]
                                    }
                                ]}
                                labels="services"
                                options={{
                                    tooltips: {
                                        enabled: true
                                    },
                                    height: 10
                                }}
                                height={10}
                            />
                        </CCardBody>
                    </CCard>
                </CCol>
            </CRow>

            <CRow>
                <CCol xs={"12"}>
                    <MaterialTable
                        title="Care Plan History"
                        columns={[
                            { title: 'Date Created', field: 'dateCreated' },
                            { title: 'Total Services', field: 'totalServices' },
                            { title: 'Pending', field: 'pending' },
                            { title: 'In Progress', field: 'inProgress' },
                            { title: 'Completed', field: 'completed' },
                        ]}
                        data={usersData}
                        actions={[
                            {
                                icon: 'edit',
                                tooltip: 'edit Form',
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
                        }}
                    />
                </CCol>
            </CRow>
            <NewCarePlan  modal={modal} toggle={toggle}/>
        </>
    )
}

export default CarePlan;