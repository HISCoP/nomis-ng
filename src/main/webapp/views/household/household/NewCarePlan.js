
import React, { useState } from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter,} from 'reactstrap';

const NewCarePlan = (props) => {
  const {
    buttonLabel,
    className
  } = props;
  const [modal, setModal] = useState(false);
  const toggle = () => setModal(!modal);




  return (
    <div>
      
      <Modal isOpen={props.modal} toggle={props.toggle} className={className} backdrop={true}>
        <ModalHeader toggle={props.toggle}>New Care Plan</ModalHeader>
        <ModalBody>
          <p> Form will be here</p>
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