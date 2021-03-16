import React from "react";
import {CCol, CRow, CWidgetIcon} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import MaterialTable from 'material-table';

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
            <CRow>
                <CCol xs="12" className={'pb-2'}>
            <MaterialTable
                title="Recent Services Form"
                columns={[
                    { title: 'Form Name', field: 'name' },
                    { title: 'Date', field: 'date' },
                ]}
                data={[
                    { name: 'Household Vunerabilty Assessment', surname: 'Baran', date: '12/11/2020 08:35 AM', birthCity: 63 },
                    { name: 'Household Followup Assessment', surname: 'Baran', date: '12/11/2020 08:35 AM', birthCity: 34 },
                ]}
                actions={[
                    {
                        icon: 'edit',
                        tooltip: 'View Form',
                        onClick: (event, rowData) => alert("You saved " + rowData.name)
                    },
                    rowData => ({
                        icon: 'delete',
                        tooltip: 'Delete Form',
                        onClick: (event, rowData) => alert("You want to delete " + rowData.name)

                    })
                ]}
                options={{
                    actionsColumnIndex: -1,
                    search: false,
                    header: false,
                    padding: 'dense',
                    maxBodyHeight: 100,
                }}
            />
                </CCol>
                <CCol xs="12" >
            <MaterialTable
                title="OVC"
                columns={[
                    { title: 'Name', field: 'name' },
                    { title: 'Age', field: 'age' },
                    { title: 'Gender', field: 'gender' },
                ]}
                data={[
                    { name: 'Amos Kindu', surname: 'Baran', age: '12 years', gender: 'Male' },
                    { name: 'Evans Masese', surname: 'Baran', age: '14 years', gender: 'Female' },
                ]}
                actions={[
                    {
                        icon: 'edit',
                        tooltip: 'View OVC',
                        onClick: (event, rowData) => alert("You saved " + rowData.name)
                    }
                ]}
                options={{
                    actionsColumnIndex: -1,
                    search: false,
                    header: false,
                    padding: 'dense',
                    maxBodyHeight: 100,
                }}
            />
                </CCol>
            </CRow>
        </CCol>
    </CRow>
    </>

    )
}

export default HouseholdDashboard;