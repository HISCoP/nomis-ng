import React, {useState} from 'react';
import {CButton, CCol, CRow, CCard, CCardBody} from "@coreui/react";
import {CChartBar} from "@coreui/react-chartjs";
import MaterialTable from 'material-table';
import NewCarePlan from './NewCarePlan';
import {toast} from "react-toastify";
import axios from "axios";
import {url} from "../../../api";
import * as CODES from "../../../api/codes";

const CarePlan = (props) => {
    const [modal, setModal] = useState(false);
    const toggle = () => setModal(!modal);
    const [loading, setLoading] = useState(false);
    const [carePlanList, setCarePlanList] = useState([]);

    React.useEffect(() => {
        fetchCarePlan();
    }, [props.householdId]);

    const fetchCarePlan = () => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
        }

        const onError = () => {
            setLoading(false);
            toast.error('Error: Could not fetch household careplans!');
        }
        axios
            .get(`${url}households/${props.householdId}/${CODES.CARE_PLAN}/formData`)
            .then(response => {
                setCarePlanList(response.data)
                if(onSuccess){
                    onSuccess();
                }
            })
            .catch(error => {
                    if(onError){
                        onError();
                    }
                }

            );
    }
    return (
        <>
            <CRow>
                <CCol xs="12" className={"pb-3"}>
                    <CButton color={"primary"} className="float-right" onClick={toggle}> New Care Plan</CButton>
                </CCol>
            </CRow>

            <CRow className={"pb-3"}>
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
                        data={carePlanList}
                    //     data={carePlanList.map((x)=>({
                    // dateCreated: new Date(),
                    // // totalServices: x.carePlan.length,
                    // // pending: x.carePlan.length,
                    // // inProgress: x.carePlan.length,
                    // // completed: x.carePlan.length
                    // }))}
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
            <NewCarePlan  modal={modal} toggle={toggle} reloadSearch={fetchCarePlan} householdId={props.householdId}/>
        </>
    )
}

export default CarePlan;