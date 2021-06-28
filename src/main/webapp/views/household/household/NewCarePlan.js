
import React, { useState } from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Input, Label, Form, FormGroup } from 'reactstrap';
import FormRenderer from "../../formBuilder/FormRenderer";

const NewCarePlan = (props) => {
  const {
    buttonLabel,
    className
  } = props;
  const [modal, setModal] = useState(false);
  const toggle = () => setModal(!modal);




  return (
    <div>
      
      <Modal isOpen={props.modal} toggle={props.toggle} className={className} backdrop={true} size={"xl"}>
        <ModalHeader toggle={props.toggle}>New Care Plan</ModalHeader>
        <ModalBody>
          <FormRenderer
              patientId={""}
              formCode={"c4666b04-9357-4229-8683-de5efed78ab7"}
              programCode={""}
              visitId={""}
              onSuccess={props.onSuccess}
              onSubmit={props.onSubmit}
          />
        </ModalBody>
        <ModalFooter>
          <Button color="primary" onClick={props.toggle}>Save</Button>{' '}
          <Button color="secondary" onClick={props.toggle}>Cancel</Button>
        </ModalFooter>
      </Modal>
    </div>
  );
  
}

export default NewCarePlan;