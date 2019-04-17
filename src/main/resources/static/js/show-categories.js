function showMakeup() {

    fetch('/fetch/category/makeup')
        .then((response) => response.json())
        .then((json) => {
            $('#customText').text('All Products');
            $('.container #content-container').remove();
            $('.container').append('<div id="content-container"></div>');

            let table =
                ` <table class="table table-hover mx-auto bg-white" >
        <thead>
        <tr class="row mx-auto mt-3">
                <th class="col-md-2 text-center">#</th>
                <th class="col-md-3 text-center">Image</th>
                <th class="col-md-2 text-center">Name</th>
                <th class="col-md-2 text-center">Price</th>
                <th class="col-md-3 text-center">Actions</th>
            </tr>
        </thead>
        <tbody>`;

            json.forEach((product, index) => {
                table +=
                    `<tr class="row mx-auto">
                     <th class="col-md-2 text-center">${index + 1}</th>
                     <td class="col-md-3 text-center">
                       <img src="${product.imageUrl}" class="product-image-home img-thumbnail" width="40" height="40"/></a>
                      </td>
                     <td class="col-md-2 text-center">${product.name}</td>
                     <td class="col-md-2 text-center">${product.price}</td>
                     <td class="col-md-3 text-center">
                    <a href="/products/details/${product.id}" class="btn btn-pink">Details</a>
                </td>
                   </tr>`;
            });
            table +=
                '<tbody>' +
                '</table>';

            $('#content-container').append(table);
        })
}

function showSun() {

    fetch('/fetch/category/sun')
        .then((response) => response.json())
        .then((json) => {
            $('#customText').text('All Products');
            $('.container #content-container').remove();
            $('.container').append('<div id="content-container"></div>');

            let table =
                ` <table class="table table-hover mx-auto bg-white" >
        <thead>
        <tr class="row mx-auto mt-3">
                <th class="col-md-2 text-center">#</th>
                <th class="col-md-3 text-center">Image</th>
                <th class="col-md-2 text-center">Name</th>
                <th class="col-md-2 text-center">Price</th>
                <th class="col-md-3 text-center">Actions</th>
            </tr>
        </thead>
        <tbody>`;

            json.forEach((product, index) => {
                table +=
                    `<tr class="row mx-auto">
                     <th class="col-md-2 text-center">${index + 1}</th>
                     <td class="col-md-3 text-center">
                       <img src="${product.imageUrl}" class="product-image-home img-thumbnail" width="40" height="40"/></a>
                      </td>
                     <td class="col-md-2 text-center">${product.name}</td>
                     <td class="col-md-2 text-center">${product.price}</td>
                     <td class="col-md-3 text-center">
                    <a href="/products/details/${product.id}" class="btn btn-pink">Details</a>
                </td>
                   </tr>`;
            });
            table +=
                '<tbody>' +
                '</table>';

            $('#content-container').append(table);
        })
}

function showHair() {

    fetch('/fetch/category/hair')
        .then((response) => response.json())
        .then((json) => {
            $('#customText').text('All Products');
            $('.container #content-container').remove();
            $('.container').append('<div id="content-container"></div>');

            let table =
                ` <table class="table table-hover mx-auto bg-white" >
        <thead>
        <tr class="row mx-auto mt-3">
                <th class="col-md-2 text-center">#</th>
                <th class="col-md-3 text-center">Image</th>
                <th class="col-md-2 text-center">Name</th>
                <th class="col-md-2 text-center">Price</th>
                <th class="col-md-3 text-center">Actions</th>
            </tr>
        </thead>
        <tbody>`;

            json.forEach((product, index) => {
                table +=
                    `<tr class="row mx-auto">
                     <th class="col-md-2 text-center">${index + 1}</th>
                     <td class="col-md-3 text-center">
                       <img src="${product.imageUrl}" class="product-image-home img-thumbnail" width="40" height="40"/></a>
                      </td>
                     <td class="col-md-2 text-center">${product.name}</td>
                     <td class="col-md-2 text-center">${product.price}</td>
                     <td class="col-md-3 text-center">
                    <a href="/products/details/${product.id}" class="btn btn-pink">Details</a>
                </td>
                   </tr>`;
            });
            table +=
                '<tbody>' +
                '</table>';

            $('#content-container').append(table);
        })
}

function showSkin() {

    fetch('/fetch/category/skin')
        .then((response) => response.json())
        .then((json) => {
            $('#customText').text('All Products');
            $('.container #content-container').remove();
            $('.container').append('<div id="content-container"></div>');

            let table =
                ` <table class="table table-hover mx-auto bg-white" >
        <thead>
        <tr class="row mx-auto mt-3">
                <th class="col-md-2 text-center">#</th>
                <th class="col-md-3 text-center">Image</th>
                <th class="col-md-2 text-center">Name</th>
                <th class="col-md-2 text-center">Price</th>
                <th class="col-md-3 text-center">Actions</th>
            </tr>
        </thead>
        <tbody>`;

            json.forEach((product, index) => {
                table +=
                    `<tr class="row mx-auto">
                     <th class="col-md-2 text-center">${index + 1}</th>
                     <td class="col-md-3 text-center">
                       <img src="${product.imageUrl}" class="product-image-home img-thumbnail" width="40" height="40"/></a>
                      </td>
                     <td class="col-md-2 text-center">${product.name}</td>
                     <td class="col-md-2 text-center">${product.price}</td>
                     <td class="col-md-3 text-center">
                    <a href="/products/details/${product.id}" class="btn btn-pink">Details</a>
                </td>
                   </tr>`;
            });
            table +=
                '<tbody>' +
                '</table>';

            $('#content-container').append(table);
        })
}

function showFragrances() {

    fetch('/fetch/category/fragrances')
        .then((response) => response.json())
        .then((json) => {
            $('#customText').text('All Products');
            $('.container #content-container').remove();
            $('.container').append('<div id="content-container"></div>');

            let table =
                ` <table class="table table-hover mx-auto bg-white" >
        <thead>
        <tr class="row mx-auto mt-3">
                <th class="col-md-2 text-center">#</th>
                <th class="col-md-3 text-center">Image</th>
                <th class="col-md-2 text-center">Name</th>
                <th class="col-md-2 text-center">Price</th>
                <th class="col-md-3 text-center">Actions</th>
            </tr>
        </thead>
        <tbody>`;

            json.forEach((product, index) => {
                table +=
                    `<tr class="row mx-auto">
                     <th class="col-md-2 text-center">${index + 1}</th>
                     <td class="col-md-3 text-center">
                       <img src="${product.imageUrl}" class="product-image-home img-thumbnail" width="40" height="40"/></a>
                      </td>
                     <td class="col-md-2 text-center">${product.name}</td>
                     <td class="col-md-2 text-center">${product.price}</td>
                     <td class="col-md-3 text-center">
                    <a href="/products/details/${product.id}" class="btn btn-pink">Details</a>
                </td>
                   </tr>`;
            });
            table +=
                '<tbody>' +
                '</table>';

            $('#content-container').append(table);
        })
}

function showBody() {

    fetch('/fetch/category/body')
        .then((response) => response.json())
        .then((json) => {
            $('#customText').text('All Products');
            $('.container #content-container').remove();
            $('.container').append('<div id="content-container"></div>');

            let table =
                ` <table class="table table-hover mx-auto bg-white" >
        <thead>
        <tr class="row mx-auto mt-3">
                <th class="col-md-2 text-center">#</th>
                <th class="col-md-3 text-center">Image</th>
                <th class="col-md-2 text-center">Name</th>
                <th class="col-md-2 text-center">Price</th>
                <th class="col-md-3 text-center">Actions</th>
            </tr>
        </thead>
        <tbody>`;

            json.forEach((product, index) => {
                table +=
                    `<tr class="row mx-auto">
                     <th class="col-md-2 text-center">${index + 1}</th>
                     <td class="col-md-3 text-center">
                       <img src="${product.imageUrl}" class="product-image-home img-thumbnail" width="40" height="40"/></a>
                      </td>
                     <td class="col-md-2 text-center">${product.name}</td>
                     <td class="col-md-2 text-center">${product.price}</td>
                     <td class="col-md-3 text-center">
                    <a href="/products/details/${product.id}" class="btn btn-pink">Details</a>
                </td>
                   </tr>`;
            });
            table +=
                '<tbody>' +
                '</table>';

            $('#content-container').append(table);
        })
}