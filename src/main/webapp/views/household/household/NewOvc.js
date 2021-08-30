
import React, { useState } from 'react';
import { Modal, ModalHeader, ModalBody } from 'reactstrap';
import * as CODES from './../../../api/codes'
import FormRenderer from './../../formBuilder/FormRenderer'
import {toast} from "react-toastify";
import axios from "axios";
import {url} from "../../../api";


const NewOvc = (props) => {
  const {
    className
  } = props;

    const currentForm = {
        code: CODES.VULNERABLE_CHILDREN_ENROLMENT_FORM,
        formName: "Vulnerable Children Form",
        options:{
            hideHeader: true
        },
    };


    const createMember = (body) => {
        const onSuccess = () => {
            toast.success('OVC saved!');
            props.reload();
            props.toggle();

        }

        const onError = () => {
            toast.error('Error: OVC not saved!');
        }
        axios
            .post(`${url}household-members`, body)
            .then(response => {
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
    const save = (e) => {
        //alert('Save Successfully');

        const data = e.data;
        const member = {details: data, householdMemberType: 2, householdId: props.householdId};
        createMember(member)

    };


  return (
    <div>
      
      <Modal isOpen={props.modal} toggle={props.toggle} className={className} backdrop={true} size='lg'>
        <ModalHeader toggle={props.toggle}>New OVC Enrolment</ModalHeader>
        <ModalBody>
            <FormRenderer
                formCode={currentForm.code}
                programCode=""
                onSubmit={save}
            />
        </ModalBody>
      </Modal>
    </div>
  );
  
}

export default NewOvc;