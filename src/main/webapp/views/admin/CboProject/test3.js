import React, {useState, useEffect} from 'react';
import axios from "axios";
import {  Row,Col,FormGroup,Label,Input,Card,CardBody} from 'reactstrap';
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
import { useSelector, useDispatch } from 'react-redux';
import { createCboProject } from '../../../actions/cboProject'
import { useHistory } from "react-router-dom";

const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))



const NewDonor = (props) => {
    let history = useHistory();
    const [loading, setLoading] = useState(false)
    const cboProjectList = useSelector(state => state.cboProjects.cboProjectList);
    const dispatch = useDispatch();
    //const [errors, setErrors] = useState({});
    const [donorList, setdonorList] = useState([]);
    const [ipList, setipList] = useState([]);
    const [cboList, setcboList] = useState([]);
    const classes = useStyles()

    const [selectedOption, setSelectedOption] = useState(null);

    const [otherDetails, setOtherDetails] = useState({ stateId: "", lgaId: "", description: "", cboId:"", implementerId: "", donorId: "", organisationUnitIds:""});
    const [provinces, setProvinces] = useState([]);
    const [lgaDetail, setLgaDetail] = useState();
    const [stateDetail, setStateDetail] = useState();
    const [states, setStates] = useState([]);

    useEffect(() => {
        //loadCboProjectList()
    }, []); //componentDidMount
  
    useEffect(() => {

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
          setStateByCountryId();
    },  [  props.showModal]);
     /*  endpoint */

     useEffect(() => {
        async function getCharacters() {
            axios
              .get(`${baseUrl}implementers`)
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


//Get States from selected country
const  setStateByCountryId=() =>{
    async function getStateByCountryId() {
        const response = await axios.get(baseUrl + 'organisation-units/hierarchy/1/2')
        const stateList = response.data;
        console.log(stateList)
        setStates(stateList);
    }
    getStateByCountryId();
}

//fetch province
const getProvinces = e => {
  setOtherDetails({ ...otherDetails, [e.target.name]: e.target.value });
        const stateId = e.target.value;
          if(stateId =="" || stateId==null){
            setLgaDetail("")
          }else{
            async function getCharacters() {
                const response = await axios.get(`${baseUrl}organisation-units/hierarchy/`+stateId+"/3");
                const newStates = states.filter(state => state.id == stateId)
                setStateDetail(newStates)
                setOtherDetails({...otherDetails, stateId:stateId})
                setProvinces(
                    Object.entries(response.data).map(([key, value]) => ({
                        label: value.name,
                        value: value.id,
                      }))
                   // response.data
                    
                    );

            }
            getCharacters();
          }
};
const getlgaObj = e => {

    const newlga = provinces.filter(lga => lga.id == e.target.value)
    setLgaDetail(newlga)
    setOtherDetails({...otherDetails, lgaId:e.target.value})
}

    const handleInputChange = e => {
        setOtherDetails ({...otherDetails,  [e.target.name]: e.target.value});
    }
    
    const organisationUnitIds = []
    const createCboAccountSetup = e => {
        const orgunitlga= selectedOption.map(item => { 
            delete item['label'];
            organisationUnitIds.push(item['value'])
            return item;
        })
        //organisationUnitIds.push(parseInt(otherDetails['stateId']))
        otherDetails['organisationUnitIds'] = organisationUnitIds
        delete otherDetails['lgaId'];
        delete otherDetails['stateId'];
        e.preventDefault()
        setLoading(true);
        const onSuccess = () => {
            setLoading(false)
            history.push('/cbo-donor-ip')
            props.toggleModal()
            
        }
        const onError = () => {
            setLoading(false)  
            history.push('/cbo-donor-ip') 
            props.toggleModal() 
        }
        
        dispatch(createCboProject(otherDetails,onSuccess, onError));
        
        return
 
    }
    


    return (

        <div >
            <ToastContainer />
            <CModal show={props.showModal} onClose={props.toggleModal} size="lg">

                <CForm >
                    <CModalHeader toggle={props.toggleModal}>NEW CBO PROJECT SETUP </CModalHeader>
                    <CModalBody>
                        <Card >
                            <CardBody>
                                <Row >
                                <Col md={6}>
                                        <FormGroup>
                                            <Label >Project Name *</Label>
                                            <Input
                                            type="text"
                                            name="description"
                                            id="description"
                                            value={otherDetails.description}
                                            onChange={handleInputChange}
                                            required
                                            />
                                           
                                        </FormGroup>
                                    </Col>
                                <Col md={6}>
                                        <FormGroup>
                                            <Label >Donors *</Label>
                                            <Input
                                            type="select"
                                            name="donorId"
                                            id="donorId"
                                            value={otherDetails.donorId}
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
                                    
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label >Implementing Partners *</Label>
                                            <Input
                                            type="select"
                                            name="implementerId"
                                            id="implementerId"
                                            value={otherDetails.implementerId}
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
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label >CBO *</Label>
                                            <Input
                                            type="select"
                                            name="cboId"
                                            id="cboId"
                                            value={otherDetails.cboId}
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
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label >State *</Label>
                                            <Input
                                                type="select"
                                                name="state"
                                                id="state"
                                                value={otherDetails.state}
                                                    onChange={getProvinces}
                                                >
                                                    <option >Please Select State</option>
                                                    {states.map((row) => (
                                                        <option key={row.id} value={row.id}>
                                                            {row.name}
                                                        </option>
                                                    ))}
                                                    
                                            </Input>
                                        </FormGroup>
                                    </Col>
                                    
                                    <Col md={6}>
                                                <CFormGroup>
                                                    <Label >LGA</Label>
                                                    <Select
                                                        defaultValue={selectedOption}
                                                        onChange={setSelectedOption}
                                                        //value={otherDetails.lga}
                                                        options={provinces}
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

