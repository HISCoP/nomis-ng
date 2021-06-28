
import React, { useState } from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Input, Label, Form, FormGroup } from 'reactstrap';
import FormRenderer from "../../formBuilder/FormRenderer";

const NewHouseHoldAssessment = (props) => {
  const {
    buttonLabel,
    className
  } = props;
  const [modal, setModal] = useState(false);
  const toggle = () => setModal(!modal);




  return (
    <div>
      
      <Modal isOpen={props.modal} toggle={props.toggle} className={className} backdrop={true} size={"xl"}>
        <ModalHeader toggle={props.toggle}>New HouseHold Assessment</ModalHeader>
        <ModalBody>
          <FormRenderer
              patientId={""}
              formCode={"5f451d7d-213c-4478-b700-69a262667b89"}
              programCode={""}
              visitId={""}
              onSuccess={props.onSuccess}
              onSubmit={props.onSubmit}
          />
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={props.toggle}>Cancel</Button>
        </ModalFooter>
      </Modal>
    </div>
  );
  
}

export default NewHouseHoldAssessment;