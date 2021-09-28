import React, { useState } from "react";
import { CCol, CRow, CButton, CAlert, CCardHeader, CCard, CCardBody, CModal, CModalBody, CModalHeader} from "@coreui/react";
import {Col, FormGroup, Input, Label, Modal, ModalBody, ModalHeader} from "reactstrap";
import moment from "moment";
import {toast, ToastContainer} from "react-toastify";
import axios from "axios";
import {url} from "../../../api";
import * as CODES from "../../../api/codes";
import FormRenderer from "../../formBuilder/FormRenderer";

const FillForm = (props) => {
    const [serviceDate, setServiceDate] = React.useState(moment().format('YYYY-MM-DD'));
    const [formList, setFormList] = React.useState([]);
    const [selectedForm, setSelectedForm] = React.useState();
    const [showFormPage, setShowForm] = React.useState(false);

    const setDate = (e) => {
        setServiceDate(e.target.value);
    }

    React.useEffect(() => {
        fetchForms();
    }, []);

    const fetchForms = () => {
        //setLoading(true);
        const onSuccess = () => {
            //setLoading(false);
        }

        const onError = () => {
            //setLoading(false);
            toast.error('Error: Could not fetch available forms!');
        }
        axios
            .get(`${url}forms`)
            .then(response => {
                setFormList(response.data.filter(x => (x.code !== CODES.VULNERABLE_CHILDREN_ENROLMENT_FORM && x.code !== CODES.CARE_GIVER_ENROLMENT_FORM && x.code !== CODES.CARE_PLAN && x.code !== CODES.HOUSEHOLD_ASSESSMENT && x.code !== CODES.Caregiver_Household_Service && x.code !== "6b8e6617-7182-40d0-9e8c-d6f33e9f4325" && x.code !== "cc07a3a6-286e-4c1e-a2ce-08a429b42d0d" && x.code !== "ca9332ef-b60b-42ac-b879-a67ebf27649d")))
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

    const openForm = () => {
        setShowForm(true);
    }

    const onSuccess = () => {
        setShowForm(false);
        //props.reloadSearch();
        toast.success("Form saved successfully!", { appearance: "success" });
       // props.toggle();
    }
    return (<>

        <ToastContainer/>
        {!showFormPage &&
            <CCard className={"mb-2"}>
            <CCardBody>
            <CRow>
            <CCol md={5}>
            <FormGroup>
            <Label >Service Date</Label>
            <Input type={"date"} onChange={setDate} value={serviceDate}/>
            </FormGroup>
            </CCol>
            <CCol md={5}>
            <FormGroup>
            <Label for="gender">Select Form</Label>
            <Input
            type="select"
            name="form"
            id="form"
            value={selectedForm}
            onChange={(e) => setSelectedForm(e.target.value)}
            required
            >
            <option value=""> </option>
            {formList.map(x => (
                <option key={x.id} value={x.code}>
                    {x.name}
                </option>
            ))}
            </Input>
            </FormGroup>
            </CCol>
            <CCol md={2}>
            <CButton color={"primary"} className={" mt-4"} onClick={openForm}>Open Form </CButton>
            </CCol>
            </CRow>
            </CCardBody>
            </CCard>
        }
        {showFormPage &&
        <CModal show={showFormPage} onClose={() => setShowForm(false)} backdrop={true} size='lg'>
            <CModalHeader closeButton>New Care Giver</CModalHeader>
            <CModalBody>
                <FormRenderer
                    formCode={selectedForm}
                    householdMemberId={props.member.id}
                    onSuccess={onSuccess}
                />
            </CModalBody>
        </CModal>
        }
        {showFormPage &&
            <CCard>
            <CCardHeader>Fill Form
            <CButton color={"primary"} className={"float-right mr-1 mb-1"} onClick={() => setShowForm(false)}>Go Back </CButton>
            </CCardHeader>
            <CCardBody>
            <FormRenderer
            formCode={selectedForm}
            householdMemberId={props.member.id}
            onSuccess={onSuccess}
            />
            </CCardBody>
            </CCard>
        }
    </>)
}

export default FillForm;