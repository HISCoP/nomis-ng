import React, {useEffect,useState} from 'react';
import MaterialTable from 'material-table';
import {
    Card,
    CardBody, 
} from 'reactstrap';
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import Typography from "@material-ui/core/Typography";
import { Link } from 'react-router-dom';
import Button from "@material-ui/core/Button";
import { FaPlus } from "react-icons/fa";
import { connect } from "react-redux";
import { fetchAllDomainServices, deleteDomainService } from "../../../actions/domainsServices";
import NewServices from './NewServices';
import {Menu,MenuList,MenuButton,MenuItem,} from "@reach/menu-button";
import "@reach/menu-button/styles.css";
import { MdModeEdit, MdDelete } from "react-icons/md";
import { toast } from "react-toastify";
import SaveIcon from "@material-ui/icons/Delete";
import CancelIcon from "@material-ui/icons/Cancel";
import {makeStyles} from "@material-ui/core/styles";
import { Modal, ModalBody, ModalHeader, Spinner, ModalFooter
} from 'reactstrap';

const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))

const DomainServiceList = (props) => {
    const [loading, setLoading] = useState('')
    const [showModal, setShowModal] = React.useState(false);
    const toggleModal = () => setShowModal(!showModal)
    const domainDetails = props.location.state && props.location.state!==null ? props.location.state : "";
    const [deleting, setDeleting] = React.useState(false);
    const [currentDomainService, setCurrenDomainService] = React.useState(null);
    const [showDeleteModal, setShowDeleteModal] = React.useState(false);
    const toggleDeleteModal = () => setShowDeleteModal(!showDeleteModal)
    const classes = useStyles()

    useEffect(() => {
            loadDomainsServices()
    }, [domainDetails.id]); //componentDidMount

    const loadDomainsServices = () => {
        setLoading('true');
            const onSuccess = () => {
                setLoading(false)
            }
            const onError = () => {
                setLoading(false)     
            }
                props.fetchAllDomainServices(domainDetails.id, onSuccess, onError);
        }; //componentDidMount

    const openNewDomainModal = (row) => {
        if(row){
            setCurrenDomainService(row)
            toggleModal();
        }
        toggleModal();    
       
    }
    const processDelete = (id) => {
        setDeleting(true);
       const onSuccess = () => {
           setDeleting(false);
           toggleDeleteModal();
           loadDomainsServices();
       };
       const onError = () => {
           setDeleting(false);
           toast.error("Something went wrong, please contact administration");
       };

       props.deleteDomainService(id, onSuccess, onError);
       }

   
       const deleteApplicationDomain = (row) => {
        setCurrenDomainService(row)
           toggleDeleteModal();
       }

    return (
        <Card>
            <CardBody>
                <Breadcrumbs aria-label="breadcrumb">
                    <Link color="inherit" to={{pathname: "/admin"}} >
                        Admin
                    </Link>
                    <Typography color="textPrimary">Domain - {domainDetails.name} </Typography>
                </Breadcrumbs>
                <br/>
                <div className={"d-flex justify-content-end pb-2"}>
                    <Button variant="contained"
                            color="primary"
                            startIcon={<FaPlus />}
                            onClick={() => openNewDomainModal(null)}
                            >
                        <span style={{textTransform: 'capitalize'}}>Add New Service </span>
                    </Button>

                </div>
                <MaterialTable
                    title="Find By Service "
                    columns={[
                        { title: "Domain ID", field: "domainId" },
                        {title: "Service Name", field: "name"},
                       
                        {title: "Domain Status", field: "status"},
                        {title: "Action", field: "action", filtering: false,},
                    ]}
                    isLoading={loading}   
                    data={props.serviceList.map((row) => ({
                        domainId: row.id,
                        name: row.name,
                   
                        status: row.archived===0 ? 'Active' : "Inactive",
                        action: ( <div>
                            <Menu>
                                <MenuButton
                                style={{
                                    backgroundColor: "#3F51B5",
                                    color: "#fff",
                                    border: "2px solid #3F51B5",
                                    borderRadius: "4px",
                                }}
                                >
                                Actions <span aria-hidden>▾</span>
                                </MenuButton>
                                <MenuList style={{ color: "#000 !important" }}>
                                <MenuItem style={{ color: "#000 !important" }} onClick={() => openNewDomainModal(row)}>
                                  
                                    <MdModeEdit size="15" />{" "}
                                    <span style={{ color: "#000" }}>Edit  </span>
                                   
                                </MenuItem>
                                <MenuItem style={{ color: "#000 !important" }} onClick={() => deleteApplicationDomain(row)}>
                            
                                    <MdDelete size="15" />{" "}
                                    <span style={{ color: "#000" }}>Delete </span>
                                 
                                </MenuItem>
                                </MenuList>
                            </Menu>
                            </div>
                            )
                        }))}  
                    
                        //overriding action menu with props.actions
                        components={props.actions}
                        options={{
                            headerStyle: {
                                backgroundColor: "#9F9FA5",
                                color: "#000",
                            },
                            searchFieldStyle: {
                                width : '200%',
                                margingLeft: '250px',
                            },
                            filtering: true,
                            exportButton: false,
                            searchFieldAlignment: 'left',
                            actionsColumnIndex: -1
                        }}
                />
            </CardBody>
            
            <NewServices toggleModal={toggleModal} showModal={showModal} domainDetails={domainDetails} loadDomainsServices={loadDomainsServices} currentDomainService={currentDomainService}/>
            {/* Delete Modal for Domain Service Area */}
            <Modal isOpen={showDeleteModal} toggle={toggleDeleteModal} >
                    <ModalHeader toggle={props.toggleDeleteModal}> Delete Domain Area  - {currentDomainService && currentDomainService.name ? currentDomainService.name : ""} </ModalHeader>
                    <ModalBody>
                        <p>Are you sure you want to proceed ?</p>
                    </ModalBody>
                <ModalFooter>
                    <Button
                        type='button'
                        variant='contained'
                        color='primary'
                        className={classes.button}
                        startIcon={<SaveIcon />}
                        disabled={deleting}
                        onClick={() => processDelete(currentDomainService.id)}
                    >
                        Delete  {deleting ? <Spinner /> : ""}
                    </Button>
                    <Button
                        variant='contained'
                        color='default'
                        onClick={toggleDeleteModal}
                        startIcon={<CancelIcon />}
                    >
                        Cancel
                    </Button>
                </ModalFooter>
        </Modal>
            {/* End of Delelte Modal for Domain Service Area */}
        </Card>
        
    );
   

}


const mapStateToProps = state => {
    return {
        serviceList: state.domainServices.services
    };
  };
  const mapActionToProps = {
    fetchAllDomainServices: fetchAllDomainServices,
    deleteDomainService:deleteDomainService
  };
  
  export default connect(mapStateToProps, mapActionToProps)(DomainServiceList);
