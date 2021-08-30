import React, {useState} from 'react';
import {CButton, CCol, CRow, CCard, CDataTable, CCardBody} from "@coreui/react";
import {Icon} from "semantic-ui-react";
import {CChartBar} from "@coreui/react-chartjs";
import MaterialTable from 'material-table';
import NewHouseHoldAssessment from './NewHouseHoldAssessment';
import {toast} from "react-toastify";
import axios from "axios";
import {url} from "../../../api";
import * as CODES from './../../../api/codes'

const Assessment = (props) => {
    const [modal, setModal] = useState(false);
    const [loading, setLoading] = useState(false);
    const [assessmentList, setAssessmentList] = useState([]);
    const toggle = () => setModal(!modal);

    React.useEffect(() => {
       fetchAssessments();
    }, [props.householdId]);

    const fetchAssessments = () => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
        }

        const onError = () => {
            setLoading(false);
            toast.error('Error: Could not fetch household assessments!');
        }
        axios
            .get(`${url}households/${props.householdId}/${CODES.HOUSEHOLD_ASSESSMENT}/formData`)
            .then(response => {
                setAssessmentList(response.data.map(x => x.data))
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
            <CRow >
                <CCol xs="12" className={"pb-3"}>
                    <CButton color={"primary"} className="float-right" onClick={toggle} > New Assessment</CButton>
                </CCol>
            </CRow>

            <CRow>
                <CCol xs={"12"}>
                    <MaterialTable
                        title="Assessment History"
                        isLoading={loading}
                        columns={[
                            { title: 'Date Created', field: 'assessmentDate' },
                            { title: 'Total Yes', field: 'totalYes' },
                            { title: 'Total No', field: 'totalNo' },
                            { title: 'Total N/A', field: 'totalNa' },
                        ]}
                        data={assessmentList}
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
            <NewHouseHoldAssessment  modal={modal} toggle={toggle} householdId={props.householdId} reloadSearch={fetchAssessments}/>
        </>
    )
}

export default Assessment;