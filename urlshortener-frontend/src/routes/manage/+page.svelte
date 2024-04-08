<script>
  import axios from "axios";
  import { onMount } from "svelte";
  import { page } from "$app/stores";
  import { user, jwt_token } from "../../store";


  const api_root = $page.url.origin;

  let urls = [];

  onMount(() => {
    getUrls();
  });

  function getUrls() {
    var config = {
      method: "get",
      url: api_root + "/api/urls" + "?user=" + $user.name,
      headers: {Authorization: "Bearer "+$jwt_token}
    };

    axios(config)
      .then(function (response) {
        urls = response.data;
      })
      .catch(function (error) {
        alert("Could not get urls");
        console.log(error);
      });
  }

  function deleteUrl(shortUrl) {
    var config = {
      method: "delete",
      url: api_root + "/api/urls/" + shortUrl,
      headers: {Authorization: "Bearer "+$jwt_token}
    };

    axios(config)
      .then(function (response) {
        getUrls();
      })
      .catch(function (error) {
        alert("Could not delete url");
        console.log(error);
      });
  }

  function shareUrl(shortUrl) {
    navigator.clipboard.writeText(shortUrl);
    alert(`Press Ok to copy the short URL ${shortUrl} to clipboard.`);
  }
</script>

<h1>Manage Urls</h1>
<table class="table">
  <thead>
    <tr>
      {#if $user.user_roles && $user.user_roles.includes("admin")}
      <th scope="col">User</th>
      {/if}
      <th scope="col">Description</th>
      <th scope="col">Timestamp</th>
      <th scope="col">Actions</th>
    </tr>
  </thead>
  <tbody>
    {#each urls as u}
      <tr>
        {#if $user.user_roles && $user.user_roles.includes("admin")}
        <td>{u.user}</td>
        {/if}
        <td>{u.description}</td>
        <td>{new Date(u.creationDate).toLocaleString()}</td>
        <td>
          <button on:click={() => deleteUrl(u.shortUrl)}>Delete</button>
          <button on:click={() => window.open(u.longUrl, "_blank")}>Open</button>
          <button on:click={() => shareUrl(`${api_root}/go/${u.shortUrl}`)}>Share</button>
        </td>
      </tr>
    {/each}
  </tbody>
</table>
