
import React, { useState } from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Input, Label, Form, FormGroup } from 'reactstrap';
import * as CODES from './../../../api/codes'
import FormRenderer from './../../formBuilder/FormRenderer'


const NewHouseHold = (props) => {
  const {
    buttonLabel,
    className
  } = props;
  const [modal, setModal] = useState(false);
  const toggle = () => setModal(!modal);

  const currentForm = {
    code: CODES.NEW_HOUSEHOLD,
    //programCode: CODES.GENERAL_SERVICE,
    formName: "Add Household",
    options:{
        hideHeader: true
    },
  };

const saveAssessment = (e) => {
    const data = e.data;
      alert('Save Successfully');
      const assessment = {details: data,
        householdMemberDTO: { details: data.householdMember, householdMemberType: 0},
        uniqueId: data.uniqueId };
      console.log(assessment);
      props.togglestatus();

  
};


  return (
    <div>
      
      <Modal isOpen={props.modal} toggle={props.toggle} className={className} backdrop={true} size='xl'>
        <ModalHeader toggle={props.toggle}>New HouseHold</ModalHeader>
        <ModalBody>
          
          <FormRenderer
          formCode={currentForm.code}
          programCode=""
          onSubmit={saveAssessment}
          />
        </ModalBody>
        {/* <ModalFooter>
          <Button color="primary" onClick={props.toggle}>Save</Button>{' '}
          <Button color="secondary" onClick={props.toggle}>Cancel</Button>
        </ModalFooter> */}
      </Modal>
      
    </div>
  );
  
}

export default NewHouseHold;