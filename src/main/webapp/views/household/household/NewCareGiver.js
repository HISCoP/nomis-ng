
import React, { useState } from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Input, Label, Form, FormGroup } from 'reactstrap';
import * as CODES from './../../../api/codes'
import FormRenderer from './../../formBuilder/FormRenderer'


const NewOvc = (props) => {
  const {
    buttonLabel,
    className
  } = props;
  const [modal, setModal] = useState(false);
  const toggle = () => setModal(!modal);
  const currentForm = {
    code: CODES.CARE_GIVER_ENROLMENT_FORM ,
    formName: "Care Giver Enrolment Form",
    options:{
      hideHeader: true
    },
  };

  const saveAssessment = (e) => {
    alert('Save Successfully');
    props.togglestatus();


  };


  return (
      <div>

        <Modal isOpen={props.modal} toggle={props.toggle} className={className} backdrop={true} size='lg'>
          <ModalHeader toggle={props.toggle}>New Care Giver</ModalHeader>
          <ModalBody>
            <FormRenderer
                formCode={currentForm.code}
                onSubmit={saveAssessment}/>
          </ModalBody>
        </Modal>
      </div>
  );

}

export default NewOvc;