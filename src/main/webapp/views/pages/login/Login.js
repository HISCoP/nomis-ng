import React, {useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { useHistory  } from "react-router-dom";
import {
  CButton,
  CCard,
  CCardBody,
  CCardGroup,
  CCol,
  CContainer,
  CForm,
  CInput,
  CInputGroup,
  CInputGroupPrepend,
  CInputGroupText,
  CRow,
} from '@coreui/react';
import { Alert} from 'reactstrap';
import CIcon from '@coreui/icons-react';
import { authentication } from "./../../../_services/authentication";
import logo from './../../../assets/images/arms.jpg';






const Login = () => {
  let history = useHistory();

  const process = e => {
    e.preventDefault();
    history.push("/dashboard")
  
  }
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [remember, setRemember] = useState("");
  const [submitText, setSubmittext] = useState("Sign In");
  const [isButtonDisabled, setIsButtonDisabled] = useState(true);
  const [helperText, setHelperText] = useState("");
  const [error, setError] = useState(false);
  const [errorStyle, setErrorStyle] = useState("")
  const [visible, setVisible] = useState(true);

  const onDismiss = () => setVisible(false);

  useEffect(() => {
    if (username.trim() && password.trim()) {
      setIsButtonDisabled(false);
    } else {
      setIsButtonDisabled(true);
    }
  }, [username, password]);

  const handleLogin = () => {
    setSubmittext("Login Please wait...")
    setIsButtonDisabled(false)
    authentication.login(username, password, remember).then(
      (user) => {
        setError(false);
        setHelperText("Login Successfully");
        history.push("/dashboard")
      },
      (error) => {
        setIsButtonDisabled(true)
        setSubmittext("Sign In")
        setError(true);
        setErrorStyle({border: "2px solid red"})
        setHelperText("Incorrect username or password");
        console.log(errorStyle)
      }
    );
  };

  const handleKeyPress = (e) => {
    if (e.keyCode === 13 || e.which === 13) {
      isButtonDisabled || handleLogin();
    }
  };
  
  return (
    <div className="c-app c-default-layout flex-row align-items-center">
     
      <CContainer >
      
        <CRow className="justify-content-center">
        
          <CCol md="8" >
                {error ?
                <>
                  <Alert color="danger" isOpen={visible} toggle={onDismiss} fade={false}>
                   Incorrect Username or Password. Please Try again...
                  </Alert>
                  </>
                  : ""
                }
            <CCardGroup>
            
              <CCard className="p-19" >
              
                <CCardBody>
                
                  <CForm onSubmit={handleLogin}>
                    <h1 style={{color:'#021F54'}}>Login</h1>
                    
                    <p className="text-muted">Sign In to your account</p>
                    <CInputGroup className="mb-3">
                      <CInputGroupPrepend>
                        <CInputGroupText>
                          <CIcon name="cil-user" />
                        </CInputGroupText>
                      </CInputGroupPrepend>
                      <CInput type="text" placeholder="Username" autoComplete="username" 
                        name="email"
                        onChange={(e) => setUsername(e.target.value)}
                        onKeyPress={(e) => handleKeyPress(e)} 
                        required
                        
                      />
                    </CInputGroup>
                   
                    <CInputGroup className="mb-4">
                      <CInputGroupPrepend>
                        <CInputGroupText>
                          <CIcon name="cil-lock-locked" />
                        </CInputGroupText>
                      </CInputGroupPrepend>
                      <CInput type="password" placeholder="Password" autoComplete="current-password" 
                        placeholder="Password"
                        onChange={(e) => setPassword(e.target.value)}
                        onKeyPress={(e) => handleKeyPress(e)}

                      />
                    </CInputGroup>
                    <CRow>
                      <CCol xs="12">
                        <CButton style={{ backgroundColor:'#021F54', color:'#fff' }} className="px-4" onClick={handleLogin} disabled={isButtonDisabled}>{submitText}</CButton>
                      </CCol>
                      <CCol xs="6" className="text-right">
                        {/* <CButton color="link" className="px-0">Forgot password?</CButton> */}
                      </CCol>

                      
                    </CRow>
                  </CForm>
               
                </CCardBody>
              </CCard>
              <CCard  style={{ backgroundColor:'#021F54' }} className="text-white py-5 d-md-down-none p-19">
                <CCardBody className="text-center" style={{ backgroundColor:'#021F54' }}>
                  <div>
                    <h1 style={{color:'#2EB85C'}}>NOMIS</h1>
                    <p> National OVC Management Information System  </p>
                    
                    <Link to="/register">
                      {/* <CButton color="primary" className="mt-3" active tabIndex={-1}>Register Now!</CButton> */}
                    </Link>
                  </div>
                </CCardBody>
                <span style={{marginRight:10, margingTop:150, margingBottom:-650}}>
                    <img src={logo} alt="Nigeria Coat of Arms" style={{width: 50, height: 50, borderRadius: 400/ 2}}  className="float-right "/>
                </span>
              </CCard>
            </CCardGroup>
          </CCol>
        </CRow>
       
      </CContainer>
  
    </div>
  )
}

export default Login
