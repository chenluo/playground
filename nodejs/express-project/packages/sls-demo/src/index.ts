import { Handler } from 'aws-lambda';
import moment from 'moment';

export const handler: Handler = async (event, context) => {
    console.log('EVENT: \n' + JSON.stringify(event, null, 2));
    const time = moment();
    console.log(`Current time: ${time.format('YYYY-MM-DD HH:mm:ss')}`);
    return {
      statusCode: 200,
      body: JSON.stringify(
        {
          message: "Go Serverless v3.0! Your function executed successfully!",
          input: event,
        },
        null,
        2
      ),
    };
};
