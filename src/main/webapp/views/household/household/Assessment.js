import React, {useState} from 'react';
import {CButton, CCol, CRow, CCard, CDataTable, CCardBody} from "@coreui/react";
import {Icon} from "semantic-ui-react";
import {CChartBar} from "@coreui/react-chartjs";
import MaterialTable from 'material-table';
import NewHouseHoldAssessment from './NewHouseHoldAssessment';

const Assessment = () => {
    const [modal, setModal] = useState(false);
    const toggle = () => setModal(!modal);
    const usersData = [
        {pending: 0, totalServices: '10', dateCreated: '2018/01/01', inProgress: '3', completed: '7'},
        {pending: 1, totalServices: '7', dateCreated: '2018/01/01', inProgress: '2', completed: '4'},
        {pending: 2, totalServices: '5', dateCreated: '2018/02/01', inProgress: '3', completed: '0'}
];
    return (
        <>
            <CRow >
                <CCol xs="12" className={"pb-3"}>
                    <CButton color={"primary"} className="float-right" onClick={toggle}> New Assessment</CButton>
                </CCol>
            </CRow>

            <CRow>
                <CCol xs={"12"}>
                    <MaterialTable
                        title="Assessment History"
                        columns={[
                            { title: 'Date Created', field: 'dateCreated' },
                            { title: 'Total Yes', field: 'totalServices' },
                            { title: 'Total No', field: 'pending' },
                            { title: 'Total N/A', field: 'inProgress' },
                        ]}
                        data={usersData}
                        actions={[
                            {
                                icon: 'edit',
                                tooltip: 'edit Form',
                                onClick: (event, rowData) => alert("You want to edit " + rowData.name)
                            },
                            rowData => ({
                                icon: 'visibility',
                                tooltip: 'View Form',
                                onClick: (event, rowData) => alert("You want to view " + rowData.name)

                            })
                        ]}
                        options={{
                            actionsColumnIndex: -1,
                            padding: 'dense',
                        }}
                    />
                </CCol>
            </CRow>
            <NewHouseHoldAssessment  modal={modal} toggle={toggle}/>
        </>
    )
}

export default Assessment;