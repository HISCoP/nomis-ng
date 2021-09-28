import React, {useState} from 'react';
import {CButton, CCol, CRow, CCard, CCardBody} from "@coreui/react";
import {CChartBar} from "@coreui/react-chartjs";
import MaterialTable from 'material-table';
import NewCarePlan from './NewCarePlan';
import {toast} from "react-toastify";
import axios from "axios";
import {url} from "../../../api";
import * as CODES from "../../../api/codes";
import FormRendererModal from "../../formBuilder/FormRendererModal";

const CarePlan = (props) => {
    const [modal, setModal] = useState(false);
    const toggle = () => setModal(!modal);
    const [loading, setLoading] = useState(false);
    const [carePlanList, setCarePlanList] = useState([]);
    const [currentForm, setCurrentForm] = useState(false);
    const [showFormModal, setShowFormModal] = useState(false);

    const onSuccess = () => {
        fetchCarePlan();
        toast.success("Form saved successfully!");
        setShowFormModal(false);
    }

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
                setCarePlanList(response.data.map((x)=>({
                    data: x,
                    dateCreated: x.data && x.data.encounterDate ? x.data.encounterDate : null,
                    inProgress:  x.data && x.data.carePlan ? [].concat.apply([], x.data.carePlan.map(c=>c.identifiedIssues)).filter(x => x.followupStatus === 'inProgress').length  : 0,
                    pending:  x.data && x.data.carePlan ? [].concat.apply([], x.data.carePlan.map(c=>c.identifiedIssues)).filter(x => x.followupStatus === 'pending').length : 0,
                    completed: x.data && x.data.carePlan ? [].concat.apply([], x.data.carePlan.map(c=>c.identifiedIssues)).filter(x => x.followupStatus === 'completed').length : 0
                })));
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

    const viewForm = (row) => {
        setCurrentForm({ ...row, type: "VIEW", formCode: CODES.CARE_PLAN   });
        setShowFormModal(true);
    }

    const editForm = (row) => {
        setCurrentForm({ ...row, type: "EDIT", formCode: CODES.CARE_PLAN  });
        setShowFormModal(true);
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
                            { title: 'Pending', field: 'pending' },
                            { title: 'In Progress', field: 'inProgress' },
                            { title: 'Completed', field: 'completed' },
                        ]}
                        // data={carePlanList.map((x)=>{
                        //    console.log(x.data)})}
                        data={carePlanList}
                        actions={[
                            {
                                icon: 'edit',
                                tooltip: 'edit Form',
                                onClick: (event, rowData) => editForm(rowData.data)
                            },
                            rowData => ({
                                icon: 'visibility',
                                tooltip: 'View Form',
                                onClick: (event, rowData) => viewForm(rowData.data)

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
                                        data: carePlanList.map(x=>x.pending)
                                    },
                                    {
                                        label: 'In Progress',
                                        backgroundColor: '#f8a121',
                                        data: carePlanList.map(x=>x.inProgress)
                                    },
                                    {
                                        label: 'Completed',
                                        backgroundColor: '#01f83a',
                                        data: carePlanList.map(x=>x.completed)
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
            <FormRendererModal
                showModal={showFormModal}
                setShowModal={setShowFormModal}
                currentForm={currentForm}
                onSuccess={onSuccess}
                //onError={onError}
                options={{modalSize:"xl"}}
            />
            <NewCarePlan  modal={modal} toggle={toggle} reloadSearch={fetchCarePlan} householdId={props.householdId}/>
        </>
    )
}

export default CarePlan;