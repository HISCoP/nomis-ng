
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
        code: CODES.VULNERABLE_CHILDREN_ENROLMENT_FORM,
        //programCode: CODES.GENERAL_SERVICE,
        formName: "Hosehold Assesment",
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
        <ModalHeader toggle={props.toggle}>New OVC</ModalHeader>
        <ModalBody>
            <FormRenderer
                formCode={currentForm.code}
                programCode=""
                onSubmit={saveAssessment}
            />
        </ModalBody>
      </Modal>
    </div>
  );
  
}

export default NewOvc;