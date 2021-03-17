import React from "react";
import { CCol, CRow, CButton} from "@coreui/react";
import ServiceHistoryPage from "../widgets/ServiceHistoryPage";
import MaterialTable from 'material-table';
import { Tab } from 'semantic-ui-react'

const ServiceHomePage = () => {


    const panes = [
        {
            menuItem: 'Household Member Services',
            render: () => <ServicePage/>,
        },
        {
            menuItem: 'Pending Forms (All unfilled supporting forms)',
            render: () => <PendingForms/>,
        }
    ]

    return (
        <Tab menu={{ secondary: true, pointing: true }} panes={panes} />
    );
}

const ServicePage = () => {
    return (
        <>
            <CRow>
                <CCol xs="12">
                    <CButton color={"primary"} className={"float-right mr-1 mb-1"}> Provide Service</CButton> {" "}
                </CCol>
                <hr />
            </CRow>
            <CRow className={"pt-3"}>
                    <CCol xs="12" >
                        <ServiceHistoryPage isHistory={false} memberId={12} />
                    </CCol>
            </CRow>
            </>
    );
}

const PendingForms = () => {
    return (
        <>
            <CRow className={"pt-3"}>
                <CCol xs="12" >
                    <MaterialTable
                        title="Pending Forms"
                        columns={[
                            { title: 'Form Name', field: 'name' },
                            { title: 'Date', field: 'date' },
                            { title: 'Service Name', field: 'service' },
                        ]}
                        data={[
                            { name: 'Child Educational Performance Assessment Tool', service: 'School performance assessment', date: '12/11/2020 08:35 AM', birthCity: 63 },
                            { name: 'Nutritional Assessment Form', service: 'Nutrition assessment, counselling, and support (NACS)', date: '12/11/2020 08:35 AM', birthCity: 34 },
                        ]}
                        actions={[
                            {
                                icon: 'edit',
                                tooltip: 'Fill Form',
                                onClick: (event, rowData) => alert("You saved " + rowData.name)
                            }
                        ]}
                        options={{
                            actionsColumnIndex: -1,
                            padding: 'dense',
                        }}
                    />
                </CCol>
            </CRow>
        </>
    );
}

export default ServiceHomePage;