import React, { Component, createContext, useContext, useEffect, useReducer, useState } from 'react';
import Form from '@rjsf/core'
import validator from '@rjsf/validator-ajv8'
import { FieldProps, RJSFSchema, UiSchema } from '@rjsf/utils';

import { Button, Input, Select, Upload } from 'antd';
import ReactDOM from 'react-dom';
const { Dragger } = Upload;

type Theme = "light" | "dark" | "system";
const ThemeContext = createContext<Theme>("system");
export const useGetTheme = () => useContext(ThemeContext)

function App() {

  const schema: RJSFSchema = {
    type: 'object',
    properties: {
      fileList: {

      },
    }
  }
  const uiSchema: UiSchema = {
    'ui:field': 'fileList',
  };

  const options = [
    { label: 'label1', value: 'value1' },
    { label: 'label2', value: 'value2' }];

  return (
    <>
      <div id='single-select-div'>
        <Select options={options} virtual={false}></Select>
      </div>
      {/* <Form schema={schema} validator={validator} onSubmit={(e) => { console.log(e.formData) }}></Form> */}
    </>
  );
}

// class UploadFormItem extends React.Component<FieldProps> {
//   render(): React.ReactNode {
//     const [comments, setComments] = useState<Map<string, string>>(new Map())
//     return (
//       <>
//         <Dragger multiple={true} name='file' action={'http://localhost:8080/upload'}
//           itemRender={(originalNode, file, fileList, { remove }) => {
//             return (
//               <div>
//                 {originalNode}
//                 <Input onChange={(value) => setComments(map => map.set(file.uid, value.target.value))} />
//                 <Button onClick={(e) => alert(comments.get(file.uid))} />
//               </div>
//             );
//           }} />
//         <p className="ant-upload-drag-icon"> </p>
//         <p className="ant-upload-text">Click or drag file to upload</p>
//         <p className="ant-upload-hint">Support for a single or bulk upload.</p>
//       </>
//     );
//   }

// }

export default App;

interface Props {
  p1: string;
  p2: boolean;
  s: Status;
}
export type Status =
  | 's1'
  | 's2';

function FirstComponent(props: Props) {
  const status: Status = 's1'
  if (status !== 's1') {

  }
  if (props.s == 's1') {

  }

  if (props.s !== 's1') {

  }
  const [enabled, setEnabled] = useState<boolean>(true);
  const [state, transit] = useReducer((from: number, action: string): number => {
    switch (action) {
      case "add":
        return from + 1;
      case "reset":
        return 0;
      default:
        throw new Error("Unknow action")
    }
  }, 0);
  useEffect(() => {
    alert("invoked:" + state)
    const a = state ? setEnabled(false) : null;
  }, [state])
  return (
    <>
      <div>
        <div>first component</div>
        <div>{props.p1}</div>
        <div>{props.p2 ? "true" : "false"}</div>
        <button disabled={!enabled} onClick={() => setEnabled(false)}>state button</button>
        <button onClick={() => transit("add")}>{state}</button>
        <button onClick={() => transit("reset")}>reset</button>
        <div>{useGetTheme()}
        </div>
      </div>
      {state ? <div>{state}</div> : null}
    </>
  )
}

function InfiniteReRenderExample() {
  const [state, setState] = useState("");
  const [label, setLabel] = useState("not triggered");

  const _ = state ? setLabel("triggered") : null;

  return (
    <>
      <div>
        <button onClick={() => setState("triggered")}>trigger too many re-render</button>
        <label>{label}</label>
      </div>
    </>
  )
}
function AvoidInfiniteReRenderExample() {
  const [state, setState] = useState("");
  const [label, setLabel] = useState("not triggered");
  if (state == "triggered" && label != "triggered") {
    setLabel("triggered")
  }
  return (
    <>
      <div>
        <button onClick={() => setState("triggered")}>trigger</button>
        <label>{label}</label>
        <button onClick={() => { setState(""); setLabel("not triggered") }}>clear state</button>
      </div>
    </>
  )
}
