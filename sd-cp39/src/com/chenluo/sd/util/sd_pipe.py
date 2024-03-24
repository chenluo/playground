import os.path

from diffusers import DiffusionPipeline
import torch

model_dir = os.path.abspath('../../../../models/')


def get_text2img_pipe(device):
    pipe = None
    model_path = os.path.abspath(model_dir + "/stable-diffusion-xl-base-1.0")

    if device == 'cpu':
        pipe = DiffusionPipeline.from_pretrained(model_path,
                                                 torch_dtype=torch.float32,  # float16 not supported for cpu
                                                 use_safetensors=True, variant="fp16", local_files_only=True)
        pipe.to('cpu')
        pipe.enable_attention_slicing()

        # pipe.unet = torch.compile(pipe.unet, mode="reduce-overhead", fullgraph=True)
    elif device == 'cuda':
        pipe = DiffusionPipeline.from_pretrained(model_path,
                                                 torch_dtype=torch.float16,
                                                 use_safetensors=True, variant="fp16", local_files_only=True)
        pipe.to('cuda')
        pipe.enable_attention_slicing()

        # pipe.unet = torch.compile(pipe.unet, mode="reduce-overhead", fullgraph=True)
    elif device == 'mps':
        if torch.backends.mps.is_available():
            pipe = DiffusionPipeline.from_pretrained(model_path,
                                                     torch_dtype=torch.float32,  # float16 produces black image only
                                                     use_safetensors=True, variant="fp16", local_files_only=True)
            pipe.to('mps')
            # pipe.enable_attention_slicing()

            # pipe.unet = torch.compile(pipe.unet, mode="reduce-overhead", fullgraph=True)
        else:
            if not torch.backends.mps.is_built():
                print("MPS not available because the current PyTorch install was not "
                      "built with MPS enabled.")
            else:
                print("MPS not available because the current MacOS version is not 12.3+ "
                      "and/or you do not have an MPS-enabled device on this machine.")
    else:
        print("invalid device type")
    return pipe


def get_text2img_pipe_with_refiner(device):
    refiner = None
    base = get_text2img_pipe(device)
    model_path = os.path.abspath(model_dir + "/stable-diffusion-xl-refiner-1.0")
    if device == 'cpu':
        refiner = DiffusionPipeline.from_pretrained(model_path,
                                                    text_encoder_2=base.text_encoder_2,
                                                    vae=base.vae,
                                                    torch_dtype=torch.float32,  # float16 not supported for cpu
                                                    use_safetensors=True, variant="fp16", local_files_only=True)
        refiner.to('cpu')
        refiner.enable_attention_slicing()

        # pipe.unet = torch.compile(pipe.unet, mode="reduce-overhead", fullgraph=True)
    elif device == 'cuda':
        refiner = DiffusionPipeline.from_pretrained(model_path,
                                                    text_encoder_2=base.text_encoder_2,
                                                    vae=base.vae,
                                                    torch_dtype=torch.float16,
                                                    use_safetensors=True, variant="fp16", local_files_only=True)
        refiner.to('cuda')
        refiner.enable_attention_slicing()

        # pipe.unet = torch.compile(pipe.unet, mode="reduce-overhead", fullgraph=True)
    elif device == 'mps':
        if torch.backends.mps.is_available():
            refiner = DiffusionPipeline.from_pretrained(model_path,
                                                        text_encoder_2=base.text_encoder_2,
                                                        vae=base.vae,
                                                        torch_dtype=torch.float32,  # float16 produces black image only
                                                        use_safetensors=True, variant="fp16", local_files_only=True)
            refiner.to('mps')
            # pipe.enable_attention_slicing()

            # pipe.unet = torch.compile(pipe.unet, mode="reduce-overhead", fullgraph=True)
        else:
            if not torch.backends.mps.is_built():
                print("MPS not available because the current PyTorch install was not "
                      "built with MPS enabled.")
            else:
                print("MPS not available because the current MacOS version is not 12.3+ "
                      "and/or you do not have an MPS-enabled device on this machine.")
    else:
        print("invalid device type")
    return base, refiner
