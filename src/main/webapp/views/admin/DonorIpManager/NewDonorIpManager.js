import React, {useState, useEffect} from 'react';
import axios from "axios";
import {  Modal, ModalHeader, ModalBody,Form,Row,Col,FormGroup,Label,Input,Card,CardBody} from 'reactstrap';
import { connect } from 'react-redux';
import MatButton from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
import SaveIcon from '@material-ui/icons/Save'
import CancelIcon from '@material-ui/icons/Cancel'
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
//import Select from "react-select/creatable";
import { createIp, updateIp  } from "../../../actions/ip";
import { Spinner } from 'reactstrap';
import { url as baseUrl } from "../../../api";



const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))

const NewDonor = (props) => {
    const [loading, setLoading] = useState(false)
    //const [showNewCbo, setShowNewCbo] = useState(false)
    const defaultValues = { id:"",  name:"", description:"", code:"", donor: "",  };
    const [formData, setFormData] = useState( defaultValues)
    //const [errors, setErrors] = useState({});
    const [donorList, setdonorList] = useState([]);
    const classes = useStyles()
  
    useEffect(() => {
        //for application CBO  edit, load form data
        //props.loadCbo();
        setFormData(props.formData ? props.formData : defaultValues);
        //setShowNewCbo(false);
        async function getCharacters() {
            axios
              .get(`${baseUrl}donors`)
              .then((response) => {
                //console.log(Object.entries(response.data));
                setdonorList(
                  Object.entries(response.data).map(([key, value]) => ({
                    label: value.name,
                    value: value.id,
                  }))
                );
              })
              .catch((error) => {
                
              });
          }
          getCharacters();
    },  [props.formData,  props.showModal]);
     /* Get list of gender parameter from the endpoint */


    const handleInputChange = e => {
        setFormData ({ ...formData, [e.target.name]: e.target.value});
    }
    

    const createIpSetup = e => {
        
        e.preventDefault()
            setLoading(true);
            const onSuccess = () => {
                setLoading(false);
                props.loadIps();
                props.toggleModal()
            }
            const onError = () => {
                setLoading(false);
                props.toggleModal()
            }
            if(formData.id){
                props.updateIp(formData.id, formData, onSuccess, onError)
                return
            }
            props.createIp(formData, onSuccess,onError)

    }


    return (

        <div >
            <ToastContainer />
            <Modal isOpen={props.showModal} toggle={props.toggleModal} size="md">

                <Form onSubmit={createIpSetup}>
                    <ModalHeader toggle={props.toggleModal}>New DONOR-IP Setup </ModalHeader>
                    <ModalBody>
                        <Card >
                            <CardBody>
                                <Row >
                                    <Col md={12}>
                                        <FormGroup>
                                            <Label for="gender">Donor *</Label>
                                            <Input
                                            type="select"
                                            name="donor"
                                            id="donor"
                                            value={defaultValues.donor}
                                            onChange={handleInputChange}
                                            required
                                            >
                                            <option value=""> </option>
                                            {donorList.map(({ label, value }) => (
                                                <option key={value} value={value}>
                                                {label}
                                                </option>
                                            ))}
                                            </Input>
                                        </FormGroup>
                                    </Col>
                                    <Col md={12}>
                                        <FormGroup>
                                            <Label for="gender">Implementing Partner *</Label>
                                            <Input
                                            type="select"
                                            name="donor"
                                            id="donor"
                                            value={defaultValues.donor}
                                            onChange={handleInputChange}
                                            required
                                            >
                                            <option value=""> </option>
                                            {donorList.map(({ label, value }) => (
                                                <option key={value} value={value}>
                                                {label}
                                                </option>
                                            ))}
                                            </Input>
                                        </FormGroup>
                                    </Col>
                                                       
                                </Row>

                                <MatButton
                                    type='submit'
                                    variant='contained'
                                    color='primary'
                                    className={classes.button}
                                    startIcon={<SaveIcon />}
                                    disabled={loading}>
                                    Save  {loading ? <Spinner /> : ""}
                                </MatButton>
                                <MatButton
                                    variant='contained'
                                    color='default'
                                    onClick={props.toggleModal}
                                    startIcon={<CancelIcon />}>
                                    Cancel
                                </MatButton>
                            </CardBody>
                        </Card>
                    </ModalBody>

                </Form>
            </Modal>
        </div>
    );
}


export default connect(null, {createIp, updateIp})(NewDonor);

