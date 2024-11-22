const promises = [];

for (let i = 0; i < 10; i++) {
    promises.push(new Promise((resolve, reject) => {
        setTimeout(() => {
            if (i % 2 === 0) {
                resolve(i);
            } else {
                reject(`${i} is rejected`);
            }
        }, 1000)
    }));
}

// Promise.all(promises).then(res => console.log(res)).catch(err => console.log(err));
Promise.allSettled(promises).then(res => console.log(res)).catch(err => console.log(err));