import React, {useState, useEffect} from 'react';
import axios from "axios";
import {  Modal, ModalHeader, ModalBody,Form,Row,Col,FormGroup,Label,Input,Card,CardBody} from 'reactstrap';
import { connect } from 'react-redux'
import {  CFormGroup } from '@coreui/react';
import MatButton from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
import SaveIcon from '@material-ui/icons/Save'
import CancelIcon from '@material-ui/icons/Cancel'
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
import Select from "react-select";
import { createIp, updateIp  } from "../../../actions/ip";
import { Spinner } from 'reactstrap';
import { url as baseUrl } from "../../../api";
import { CModal, CModalHeader, CModalBody,CForm} from '@coreui/react'

const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))

const options = [
    { value: 'Kwali', label: 'Kwali' },
    { value: 'Bwari', label: 'Bwari' },
    { value: 'Amac', label: 'Amac' },
  ];

const NewDonor = (props) => {
    const [loading, setLoading] = useState(false)
    //const [showNewCbo, setShowNewCbo] = useState(false)
    const defaultValues = { id:"",  name:"", description:"", code:"", donor: "",  };
    const [formData, setFormData] = useState( defaultValues)
    //const [errors, setErrors] = useState({});
    const [donorList, setdonorList] = useState([]);
    const [ipList, setipList] = useState([]);
    const [cboList, setcboList] = useState([]);
    const classes = useStyles()

    const [selectedOption, setSelectedOption] = useState(null);
  
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
     /*  endpoint */

     useEffect(() => {
        async function getCharacters() {
            axios
              .get(`${baseUrl}ips`)
              .then((response) => {
                //console.log(Object.entries(response.data));
                setipList(
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
    },  []);
     /*  endpoint */


     useEffect(() => {
        async function getCharacters() {
            axios
              .get(`${baseUrl}cbos`)
              .then((response) => {
                //console.log(Object.entries(response.data));
                setcboList(
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
    },  []);
     /*  endpoint */

     useEffect(() => {
        async function getCharacters() {
            try {
                const response = await axios(
                    baseUrl+ "cbos"
                );
                const body = response.data;
                setcboList(
                    body.map(({ display, id }) => ({ label: display, value: id }))
                );
            } catch (error) {
            }
        }
        getCharacters();
    }, []);

    const handleInputChange = e => {
        setFormData ({  [e.target.name]: e.target.value});
    }
    
    const createCboAccountSetup = e => {
        const orgunitlga= selectedOption.map(item => { 
            delete item['label'];
            return item;
        })
        e.preventDefault()
        console.log(orgunitlga);
        return
 
    }


    return (

        <div >
            <ToastContainer />
            <CModal show={props.showModal} onClose={props.toggleModal} size="md">

                <CForm >
                    <CModalHeader toggle={props.toggleModal}>NEW CBO-DONOR-IMPLEMENTING PARTNERS SETUP </CModalHeader>
                    <CModalBody>
                        <Card >
                            <CardBody>
                                <Row >
                                <Col md={12}>
                                        <FormGroup>
                                            <Label for="gender">Donors *</Label>
                                            <Input
                                            type="select"
                                            name="donor"
                                            id="donor"
                                            //value={defaultValues.donor}
                                            //onChange={handleInputChange}
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
                                            <Label for="gender">Implementing Partners *</Label>
                                            <Input
                                            type="select"
                                            name="donor"
                                            id="donor"
                                            //value={defaultValues.donor}
                                            onChange={handleInputChange}
                                            required
                                            >
                                            <option value=""> </option>
                                            {ipList.map(({ label, value }) => (
                                                <option key={value} value={value}>
                                                {label}
                                                </option>
                                            ))}
                                            </Input>
                                        </FormGroup>
                                    </Col>
                                    <Col md={12}>
                                        <FormGroup>
                                            <Label for="gender">CBO *</Label>
                                            <Input
                                            type="select"
                                            name="donor"
                                            id="donor"
                                            //value={defaultValues.donor}
                                            onChange={handleInputChange}
                                            required
                                            >
                                            <option value=""> </option>
                                            {cboList.map(({ label, value }) => (
                                                <option key={value} value={value}>
                                                {label}
                                                </option>
                                            ))}
                                            </Input>
                                        </FormGroup>
                                    </Col>
                                    <Col md={12}>
                                        <FormGroup>
                                            <Label for="gender">State *</Label>
                                            <Input
                                            type="select"
                                            name="donor"
                                            id="donor"
                                            //value={defaultValues.donor}
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
                                                <CFormGroup>
                                                    <Label for="maritalStatus">LGA</Label>
                                                    <Select
                                                        defaultValue={selectedOption}
                                                        onChange={setSelectedOption}
                                                        options={options}
                                                        isMulti="true"
                                                    />
                                                   
                                                </CFormGroup>

                                            </Col>
                                </Row>

                                <MatButton
                                    type='submit'
                                    variant='contained'
                                    color='primary'
                                    className={classes.button}
                                    startIcon={<SaveIcon />}
                                    disabled={loading}
                                    onClick={createCboAccountSetup}
                                    
                                    >
                                    
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
                    </CModalBody>

                </CForm>
            </CModal>
        </div>
    );
}



export default connect(null, {createIp, updateIp})(NewDonor);

